package com.serverside.dt.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serverside.dt.commands.CalculateTaxCommand;
import com.serverside.dt.dtos.DividendEventDTO;
import com.serverside.dt.dtos.DividendEventRequestDTO;
import com.serverside.dt.dtos.DividendEventResponseDTO;
import com.serverside.dt.entities.Account;
import com.serverside.dt.entities.AccountStock;
import com.serverside.dt.entities.DividendEvent;
import com.serverside.dt.entities.DividendFrequency;
import com.serverside.dt.entities.Stock;
import com.serverside.dt.entities.StockDividendDetails;
import com.serverside.dt.events.PayoutDateAddedEvent;
import com.serverside.dt.events.PayoutDateRemovedEvent;
import com.serverside.dt.mappers.DividendEventMapper;
import com.serverside.dt.mappers.StockDividendDetailsMapper;
import com.serverside.dt.repositories.AccountRepository;
import com.serverside.dt.repositories.AccountStockRepository;
import com.serverside.dt.repositories.DividendEventRepository;
import com.serverside.dt.repositories.DividendFrequencyRepository;
import com.serverside.dt.repositories.StockDividendDetailsRepository;
import com.serverside.dt.repositories.StockRepository;
import com.serverside.dt.services.DividendEventService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DividendEventServiceImpl implements DividendEventService {

    private final DividendEventRepository repo;
    private final DividendEventMapper mapper;
    @Autowired
    private ApplicationEventPublisher eventPublisher;
    private final CalculateTaxCommand calculateTax;
    private final StockDividendDetailsMapper stockDividendDetailsMapper;
    private final AccountRepository accountRepo;
    private final AccountStockRepository accountStockRepo;
    private final StockRepository stockRepo;
    private final DividendFrequencyRepository dividendFrequencyRepo;
    private final StockDividendDetailsRepository stockDividendDetailsRepo;
    private final DividendEventRepository dividendEventRepository;

    

    @Override
    public List<DividendEventDTO> getAll() {
        return repo.findAll().stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public DividendEventDTO getById(UUID id) {
        DividendEvent entity = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("DividendEvent not found: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<DividendEventDTO> getByAccount(UUID accountId) {
        return repo.findByAccountId(accountId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public List<DividendEventDTO> getByStock(UUID stockId) {
        return repo.findByStockId(stockId).stream()
                .map(mapper::toDTO)
                .toList();
    }

    @Override
    public DividendEventDTO create(DividendEventDTO dto) {

        // Validate FK references
        if (!accountRepo.existsById(dto.getAccountId())) {
            throw new RuntimeException("Account not found: " + dto.getAccountId());
        }

        if (!stockRepo.existsById(dto.getStockId())) {
            throw new RuntimeException("Stock not found: " + dto.getStockId());
        }

        if (!stockDividendDetailsRepo.existsById(dto.getStockDividendDetailId())) {
            throw new RuntimeException("StockDividendDetails not found: " + dto.getStockDividendDetailId());
        }

        DividendEvent entity = mapper.toEntity(dto);

        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedDate(now);
        entity.setLastUpdatedDate(now);

        DividendEvent saved = repo.save(entity);
        return mapper.toDTO(saved);
    }

    @Override
    public DividendEventDTO update(UUID id, DividendEventDTO dto) {
        DividendEvent existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("DividendEvent not found: " + id));

        // Validate FK references
        if (!accountRepo.existsById(dto.getAccountId())) {
            throw new RuntimeException("Account not found: " + dto.getAccountId());
        }

        if (!stockRepo.existsById(dto.getStockId())) {
            throw new RuntimeException("Stock not found: " + dto.getStockId());
        }

        if (!stockDividendDetailsRepo.existsById(dto.getStockDividendDetailId())) {
            throw new RuntimeException("StockDividendDetails not found: " + dto.getStockDividendDetailId());
        }

        existing.setAccountId(dto.getAccountId());
        existing.setStockId(dto.getStockId());
        existing.setStockDividendDetailId(dto.getStockDividendDetailId());
        existing.setSharesAtEvent(dto.getSharesAtEvent());
        existing.setTotalAmount(dto.getTotalAmount());
        existing.setFxRate(dto.getFxRate());
        existing.setLastUpdatedDate(LocalDateTime.now());

        DividendEvent saved = repo.save(existing);
        return mapper.toDTO(saved);
    }

    @Override
    public void delete(UUID id) {
        if (!repo.existsById(id)) {
            throw new RuntimeException("DividendEvent not found: " + id);
        }
        repo.deleteById(id);
    }
    @Override
    public DividendEvent saveDividendEvent(DividendEvent dividendEvent) {
    	DividendEvent result = dividendEventRepository.save(dividendEvent);
    	return result;
    }
    public List<DividendEventResponseDTO> getAllDividendEvents() {

        List<DividendEvent> events = dividendEventRepository.findAll();

        List<DividendEventResponseDTO>dividendEventResponse = events.stream().map(event -> {

            UUID stockId = event.getStockId();
            UUID accountId = event.getAccountId();
            UUID detailId = event.getStockDividendDetailId();

            Stock stock = stockRepo.findById(stockId)
                    .orElseThrow(() -> new IllegalStateException("Stock not found: " + stockId));

            StockDividendDetails details = stockDividendDetailsRepo.findById(detailId)
                    .orElseThrow(() -> new IllegalStateException("Dividend details not found: " + detailId));

            Account account = accountRepo.findById(accountId)
                    .orElseThrow(() -> new IllegalStateException("Account not found: " + accountId));

            AccountStock accountStock = accountStockRepo
                    .findByAccountIdAndStockId(accountId, stockId)
                    .orElseThrow(() -> new IllegalStateException("AccountStock not found"));

            DividendFrequency frequency = dividendFrequencyRepo
                    .findById(stock.getPayoutFrequency())
                    .orElseThrow(() -> new IllegalStateException("Frequency not found"));

            return DividendEventResponseDTO.builder()
                    .id(event.getId().toString())
                    .account(account.getAccountNumber())
                    .amount(details.getDividendPerShare().multiply(accountStock.getShares()))
                    .currency(stock.getCurrencyCode())
                    .dividendPerShare(details.getDividendPerShare())
                    .frequency(String.valueOf(frequency.getPeriodsPerYear()))
                    .payoutDate(details.getPayoutDate())
                    .fxRate(event.getFxRate())
                    .shares(accountStock.getShares())
                    .source("historical")
                    .stockId(stock.getId().toString())
                    .ticker(stock.getTicker())
                    .build();
        }).toList();
        dividendEventResponse.stream().forEach(der->calculateTax.calculate(der.getAccount(),der));
		return dividendEventResponse;
    }
    @Transactional
    public void addPayoutDate(DividendEventRequestDTO dto) {

        UUID stockId = UUID.fromString(dto.getStockId());

        // 1. Resolve stock
        Stock stock = stockRepo.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found: " + dto.getStockId()));

        // 2. Resolve frequency
        DividendFrequency frequency = dividendFrequencyRepo
                .findByPeriodsPerYear(Integer.valueOf(dto.getFrequency()))
                .orElseThrow(() -> new IllegalArgumentException("Frequency not found: " + dto.getFrequency()));

        
        Optional<StockDividendDetails>existingStockDividendDetails = stockDividendDetailsRepo
        .findTopByStockIdOrderByExDateDesc(stockId);
        StockDividendDetails details = null;
        if(existingStockDividendDetails.isPresent())
        {
        	details = existingStockDividendDetails.get();
        	if(details.getPayoutDate() == null || details.getPayoutDate().equals(""))
        	{

                details.setDividendPerShare(dto.getDividendPerShare());
        		details.setPayoutDate(LocalDate.parse(dto.getPayoutDate()));
        		details.setLastUpdatedDate(LocalDateTime.now());
        	}
        	else
        	{
        		 // 3. Determine last ex-date
                LocalDate lastExDate = LocalDate.now().plusDays((12 / frequency.getPeriodsPerYear()) *30);
        		
                details = StockDividendDetails.builder()
                        .stockId(stock.getId())
                        .dividendPerShare(dto.getDividendPerShare())
                        .exDate(lastExDate)
                        .payoutDate(LocalDate.parse(dto.getPayoutDate()))
                        .createdDate(LocalDateTime.now())
                        .lastUpdatedDate(LocalDateTime.now())
                        .build();
        	}        	
        	
        }
        else
        {
        	 // 3. Determine last ex-date
            LocalDate lastExDate = LocalDate.now().plusDays((12 / frequency.getPeriodsPerYear()) *30);

            // 4. Create NEW StockDividendDetails row
            details = StockDividendDetails.builder()
                    .stockId(stock.getId())
                    .dividendPerShare(dto.getDividendPerShare())
                    .exDate(lastExDate)
                    .payoutDate(LocalDate.parse(dto.getPayoutDate()))
                    .createdDate(LocalDateTime.now())
                    .lastUpdatedDate(LocalDateTime.now())
                    .build();
        }
        StockDividendDetails dets = stockDividendDetailsRepo.save(details);
    	eventPublisher.publishEvent(new PayoutDateAddedEvent(stockDividendDetailsMapper.toDto(dets)));

      
    }
    @Override    
    public void removePayoutDate(String stockId, String payoutDate, String accountId) {
        
        LocalDate payoutDateParsed = LocalDate.parse(payoutDate);
        eventPublisher.publishEvent(new PayoutDateRemovedEvent(stockId, accountId, payoutDateParsed));
       
    }

	@Override
	@Transactional
	public void delete(String stockId, LocalDate payoutDate) {	
		UUID stockIdUUID = UUID.fromString(stockId);
   	 // 1. Find the StockDividendDetails row
       StockDividendDetails detail = stockDividendDetailsRepo
               .findByStockIdAndPayoutDate(stockIdUUID, payoutDate)
               .orElseThrow(() -> new IllegalStateException(
                       "No StockDividendDetails found for stockId=" + stockId + " and payoutDate=" + payoutDate
               ));

       // ⭐ 2. Delete ALL DividendEvents tied to this payout (NOT just one account)
       dividendEventRepository.deleteByStockDividendDetailId(detail.getId());

       // ⭐ 3. Delete the StockDividendDetails record itself
       stockDividendDetailsRepo.delete(detail);
		
	}


    

}
