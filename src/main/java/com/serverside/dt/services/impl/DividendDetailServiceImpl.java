package com.serverside.dt.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serverside.dt.dtos.DividendDetailTO;
import com.serverside.dt.entities.Account;
import com.serverside.dt.entities.DividendEvent;
import com.serverside.dt.entities.DividendFrequency;
import com.serverside.dt.entities.Stock;
import com.serverside.dt.entities.StockDividendDetails;
import com.serverside.dt.events.PayoutDateAddedEvent;
import com.serverside.dt.mappers.StockDividendDetailsMapper;
import com.serverside.dt.repositories.AccountRepository;
import com.serverside.dt.repositories.DividendEventRepository;
import com.serverside.dt.repositories.DividendFrequencyRepository;
import com.serverside.dt.repositories.StockDividendDetailsRepository;
import com.serverside.dt.repositories.StockRepository;
import com.serverside.dt.services.DividendDetailService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DividendDetailServiceImpl implements DividendDetailService {

    private final StockDividendDetailsRepository stockDividendDetailsRepo;
    private final DividendFrequencyRepository dividendFrequencyRepo;
    private final DividendEventRepository dividendEventRepo;
    private final AccountRepository accountRepo;
    private final StockRepository stockRepo;
    private final ApplicationEventPublisher eventPublisher;
    private final StockDividendDetailsMapper stockDividendDetailsMapper;

	@Override
	public DividendDetailTO create(DividendDetailTO detailTO) {
		
		UUID stockId = UUID.fromString(detailTO.getStockId());
		DividendFrequency frequency = dividendFrequencyRepo
                .findByPeriodsPerYear(Integer.valueOf(detailTO.getFrequency()))
                
                .orElseThrow(() -> new IllegalArgumentException("Frequency not found: " + detailTO.getFrequency()));
		
		 Optional<StockDividendDetails>existingStockDividendDetails = stockDividendDetailsRepo
			        .findTopByStockIdOrderByExDateDesc(stockId);
			        StockDividendDetails details = null;
			        if(existingStockDividendDetails.isPresent())
			        {
			        	details = existingStockDividendDetails.get();
			        	if(details.getPayoutDate() == null || details.getPayoutDate().equals(""))
			        	{

			                details.setDividendPerShare(detailTO.getDividendPerShare());
			        		details.setPayoutDate(detailTO.getPayoutDate());
			        		details.setExDate(detailTO.getExDate());
			        		details.setLastUpdatedDate(LocalDateTime.now());
			        	}
			        	else
			        	{
			        		
			                details = StockDividendDetails.builder()
			                        .stockId(stockId)
			                        .dividendPerShare(detailTO.getDividendPerShare())
			                        .exDate(detailTO.getExDate())
			                        .payoutDate(detailTO.getPayoutDate())
			                        .createdDate(LocalDateTime.now())
			                        .lastUpdatedDate(LocalDateTime.now())
			                        .build();
			        	}        	
			        	
			        }
			        else
			        {			        	

			            // 4. Create NEW StockDividendDetails row
			            details = StockDividendDetails.builder()
			                    .stockId(stockId)
			                    .dividendPerShare(detailTO.getDividendPerShare())
			                    .exDate(detailTO.getExDate())
			                    .payoutDate(detailTO.getPayoutDate())
			                    .createdDate(LocalDateTime.now())
			                    .lastUpdatedDate(LocalDateTime.now())
			                    .build();
			        }
		 StockDividendDetails dets = stockDividendDetailsRepo.save(details);
		 eventPublisher.publishEvent(new PayoutDateAddedEvent(stockDividendDetailsMapper.toDto(dets)));
		 detailTO.setDividendDetailId(dets.getId().toString());
		 return detailTO;
		
	}

	@Override
	public Map<String, List<DividendDetailTO>> getAllDividendDetails(String accountNumber, String strStockId) {
		
		Map<String,List<DividendDetailTO>>results = new HashMap<>();
		UUID stockId = UUID.fromString(strStockId);
         List<StockDividendDetails> details = stockDividendDetailsRepo.findByStockId(stockId)
        		 .orElseGet(ArrayList::new);      

         Set<UUID> allDetailIdsForStock = details.stream().map(StockDividendDetails::getId).collect(Collectors.toSet());
         Account account = accountRepo.findByAccountNumber(accountNumber)
                 .orElseThrow(() -> new IllegalStateException("Account not found: " + accountNumber));
         Stock stock = stockRepo.findById(stockId)
                 .orElseThrow(() -> new IllegalStateException("Stock not found: " + stockId));
         
         
         DividendFrequency frequency = dividendFrequencyRepo
                 .findById(stock.getPayoutFrequency())
                 .orElseThrow(() -> new IllegalStateException("Frequency not found"));
         
         List<DividendEvent>relevantDividendEvents = dividendEventRepo.findByStockIdAndAccountId(stockId, account.getId());
         List<UUID>filteredDetails = relevantDividendEvents.stream().filter(event->allDetailIdsForStock.contains(event.getStockDividendDetailId())).map(e->e.getStockDividendDetailId()).collect(Collectors.toList());
         LocalDate today =LocalDate.now();
         List<DividendDetailTO>upcoming = new ArrayList<>();
         List<DividendDetailTO>past = new ArrayList<>();
         
         details = details.stream().filter(detail-> filteredDetails.contains(detail.getId())).collect(Collectors.toList());
         
         for(int i_eachDividendDetail = 0; i_eachDividendDetail < details.size();i_eachDividendDetail++)
         {
        	 StockDividendDetails detail = details.get(i_eachDividendDetail);
        	 DividendEvent relevantEvent = null;
        	 for(int i_relevantEvent =0 ; i_relevantEvent < relevantDividendEvents.size();i_relevantEvent++)
        	 {
        		 relevantEvent = relevantDividendEvents.get(i_relevantEvent);
        		 if(relevantEvent.getStockDividendDetailId().equals(detail.getId()))
        		 {
        			break;
        		 }
        	 }
        	
        	 
        	 if(detail.getPayoutDate().isAfter(today))
        	 {
        		 upcoming.add(
        		 DividendDetailTO.builder().accountNumber(accountNumber)
        		 .dividendDetailId(detail.getId().toString())
        		 .dividendPerShare(detail.getDividendPerShare())
        		 .payoutDate(detail.getPayoutDate())
        		 .eligibleShares(relevantEvent.getSharesAtEvent())
        		 .exDate(detail.getExDate())
        		 .stockId(stockId.toString())
        		 .frequency(frequency.getPeriodsPerYear()).build()
        		 );
        	 }
        	 else
        	 {
        		 past.add(
                		 DividendDetailTO.builder().accountNumber(accountNumber)
                		 .dividendDetailId(detail.getId().toString())
                		 .dividendPerShare(detail.getDividendPerShare())
                		 .payoutDate(detail.getPayoutDate())
                		 .eligibleShares(relevantEvent.getSharesAtEvent())
                		 .exDate(detail.getExDate())
                		 .stockId(stockId.toString())
                		 .frequency(frequency.getPeriodsPerYear()).build()
                		 );
        	 }
         }
         results.put("Upcoming", upcoming);
         results.put("Past",past);
         return results;
		
	}

	@Override
	@Transactional
	public void delete(String id, String stockId, String accountNumber) {
		
		UUID dividendDetailsId = UUID.fromString(id);
		Account account = accountRepo.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new IllegalStateException("Account not found: " + accountNumber));		
		dividendEventRepo.deleteByStockIdAndStockDividendDetailIdAndAccountId(UUID.fromString(stockId), dividendDetailsId, account.getId());
		stockDividendDetailsRepo.deleteById(dividendDetailsId);
		
	}

	@Override
	@Transactional
	public void update(DividendDetailTO detailTO) {
		UUID dividendDetailsId = UUID.fromString(detailTO.getDividendDetailId());
		UUID stockId = UUID.fromString(detailTO.getStockId());
		StockDividendDetails detail = stockDividendDetailsRepo.findById(dividendDetailsId)
				.orElseThrow(()->new IllegalStateException("Dividend detail not found: " + dividendDetailsId));
		Account account = accountRepo.findByAccountNumber(detailTO.getAccountNumber())
                .orElseThrow(() -> new IllegalStateException("Account not found: " + detailTO.getAccountNumber()));	
		detail.setDividendPerShare(detailTO.getDividendPerShare());
		detail.setExDate(detailTO.getExDate());
		detail.setPayoutDate(detailTO.getPayoutDate());
		detail.setLastUpdatedDate(LocalDateTime.now());
		List<DividendEvent>relevantDividendEvents = dividendEventRepo.findByStockIdAndAccountId(stockId, account.getId());
		relevantDividendEvents = relevantDividendEvents.stream().filter(event->event.getStockDividendDetailId().equals(dividendDetailsId)).collect(Collectors.toList());
		if (relevantDividendEvents.size() > 0) {
			DividendEvent relevantEvent = relevantDividendEvents.get(0);
			relevantEvent.setSharesAtEvent(detailTO.getEligibleShares());
			dividendEventRepo.save(relevantEvent);
		}
		stockDividendDetailsRepo.save(detail);
		
	}
   

}
