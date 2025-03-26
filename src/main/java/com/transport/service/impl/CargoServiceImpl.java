package com.transport.service.impl;

import com.transport.api.dto.CargoDto;
import com.transport.api.exception.NoSuchEntityException;
import com.transport.api.mapper.CargoMapper;
import com.transport.model.Cargo;
import com.transport.repository.CargoRepository;
import com.transport.repository.SizeRepository;
import com.transport.service.CargoService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CargoServiceImpl implements CargoService {
    private final CargoRepository cargoRepository;
    private final SizeRepository sizeRepository;
    private final CargoMapper cargoMapper;

    @Override
    public List<CargoDto> getCargos(Integer pageNo, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Cargo> pagedResult = cargoRepository.findAll(paging);
        if (pagedResult.hasContent()) {
            return cargoMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public List<CargoDto> getCargosByName(Integer pageNo, Integer pageSize, String sortBy, String name) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Cargo> pagedResult = cargoRepository.findByName(name, paging);
        if (pagedResult.hasContent()) {
            return cargoMapper.convert(pagedResult.getContent());
        } else {
            return new ArrayList<>();
        }
    }

    @Override
    public CargoDto findById(Long id) {
        return cargoMapper.convert(cargoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Cargo with id: %s doesn't exist", id))));
    }

    @Transactional
    @Override
    public void createCargo(CargoDto cargoDto) {
        Cargo cargo = cargoMapper.convert(cargoDto);
        sizeRepository.save(cargo.getSize());
        cargoRepository.save(cargo);
    }

    @Transactional
    @Override
    public CargoDto updateCargo(Long id, CargoDto newCargoDto) {
        var cargo = cargoRepository.findById(id)
                .orElseThrow(() -> new NoSuchEntityException(String.format("Cargo with id: %s doesn't exist", id)));
        sizeRepository.findById(cargo.getSize().getId())
            .orElseThrow(() -> new NoSuchEntityException(
                String.format("Size with id: %s doesn't exist", cargo.getSize().getId())));
        cargoMapper.toCargo(cargo, newCargoDto);
        return cargoMapper.convert(cargo);
    }

    @Transactional
    @Override
    public void deleteCargo(Long id) {
        cargoRepository.deleteById(id);
    }
}
