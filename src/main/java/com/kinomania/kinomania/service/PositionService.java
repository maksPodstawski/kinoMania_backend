package com.kinomania.kinomania.service;

import com.kinomania.kinomania.entity.Position;
import com.kinomania.kinomania.repository.PositionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionRepository positionRepository;

    public Position getPositionById(Long positionId) {
        return positionRepository.findById(positionId).orElseThrow(() -> new RuntimeException("Position not found"));
    }
}
