package com.example.PING.service;

import com.example.PING.dto.DomainRequestDto;
import com.example.PING.dto.DomainResponseDto;
import com.example.PING.entity.Domain;
import com.example.PING.repository.DomainRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DomainService {
    private final DomainRepository domainRepository;

    @Transactional
    public DomainResponseDto createDomain(DomainRequestDto domainRequestDto) {
        Domain domain = new Domain();
        domain.setDomain(domainRequestDto.getDomain());
        // Portfolio 설정 추가 필요
        return convertToResponseDto(domainRepository.save(domain));
    }

    @Transactional(readOnly = true)
    public List<DomainResponseDto> getAllDomains() {
        return domainRepository.findAll().stream()
                .map(this::convertToResponseDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public DomainResponseDto getDomainById(Long domainId) {
        return convertToResponseDto(domainRepository.findById(domainId).orElse(null));
    }

    @Transactional
    public void deleteDomain(Long domainId) {
        domainRepository.deleteById(domainId);
    }

    private DomainResponseDto convertToResponseDto(Domain domain) {
        DomainResponseDto dto = new DomainResponseDto();
        dto.setDomainId(domain.getDomainId());
        dto.setDomain(domain.getDomain());
        return dto;
    }
}
