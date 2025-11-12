package com.example.masterdetailtable.service;


import com.example.masterdetailtable.Repository.DetailRepository;
import com.example.masterdetailtable.Repository.MasterRepository;
import com.example.masterdetailtable.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MasterDetailService {

    private final MasterRepository masterRepository;

    private final DetailRepository detailRepository;

    public MasterDetailService(MasterRepository masterRepository, DetailRepository detailRepository) {
        this.masterRepository = masterRepository;
        this.detailRepository = detailRepository;
    }

    // Master operations
    public List<Master> getAllMasters() {
        return masterRepository.findAll();
    }

    public Optional<Master> getMasterById(Long id) {
        return masterRepository.findById(id);
    }

    public Master saveMaster(Master master) {
        return masterRepository.save(master);
    }

    @Transactional
    public void deleteMaster(Long id) {
        masterRepository.deleteById(id);
    }

    // Detail operations
    public List<Detail> getDetailsByMasterId(Long masterId) {
        return masterRepository.findById(masterId)
                .map(Master::getDetails)
                .orElse(List.of());
    }

    @Transactional
    public Detail addDetailToMaster(Long masterId, Detail detail) {
        return masterRepository.findById(masterId).map(master -> {
            master.addDetail(detail);
            return detailRepository.save(detail);
        }).orElseThrow(() -> new RuntimeException("Master not found"));
    }

    @Transactional
    public void deleteDetail(Long detailId) {
        detailRepository.deleteById(detailId);
    }

    public Optional<Detail> getDetailById(Long id) {
        return detailRepository.findById(id);
    }
}