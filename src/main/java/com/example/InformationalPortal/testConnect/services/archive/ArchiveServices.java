package com.example.InformationalPortal.testConnect.services.archive;

import com.example.InformationalPortal.testConnect.models.archive.ArchiveAccrual;
import com.example.InformationalPortal.testConnect.models.archive.ArchivePayment;
import com.example.InformationalPortal.testConnect.repositories.archive.ArchiveAccrualsRepository;
import com.example.InformationalPortal.testConnect.repositories.archive.ArchivePaymentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArchiveServices {
    private final ArchiveAccrualsRepository archiveAccrualsRepository;
    private final ArchivePaymentRepository archivePaymentRepository;

    private String OwnerId;

    public String getOwnerId() {
        return OwnerId;
    }

    public void setOwnerId(String ownerId) {
        OwnerId = ownerId;
    }

    public ArchiveServices(ArchiveAccrualsRepository archiveAccrualsRepository, ArchivePaymentRepository archivePaymentRepository) {
        this.archiveAccrualsRepository = archiveAccrualsRepository;
        this.archivePaymentRepository = archivePaymentRepository;
    }

    public List<ArchiveAccrual> getAllAccruals(String ownerId) {
        return (List<ArchiveAccrual>) archiveAccrualsRepository.findAllByOwnerId(ownerId);
    }

    public List<ArchiveAccrual> getAllAccrualsNew_Old(String ownerId) {
        return (List<ArchiveAccrual>) archiveAccrualsRepository.findAllByOwnerIdOrderByDateAccrualsDesc(ownerId);
    }

    public List<ArchiveAccrual> getAllAccrualsOld_New(String ownerId) {
        return (List<ArchiveAccrual>) archiveAccrualsRepository.findAllByOwnerIdOrderByDateAccrualsAsc(ownerId);
    }
    //---------------------------------------------------------------------------------------------------------------------
    public List<ArchivePayment> getAllPayments(String ownerId) {
        return (List<ArchivePayment>) archivePaymentRepository.findAllByOwnerId(ownerId);
    }

    public List<ArchivePayment> getAllPaymentsNew_Old(String ownerId) {
        return (List<ArchivePayment>) archivePaymentRepository.findAllByOwnerIdOrderByDatePaymentDesc(ownerId);
    }

    public List<ArchivePayment> getAllPaymentsOld_New(String ownerId) {
        return (List<ArchivePayment>) archivePaymentRepository.findAllByOwnerIdOOrderByDatePaymentAsc(ownerId);
    }

    public void deleteAccrual(Integer id) {
        archiveAccrualsRepository.deleteById(id);
    }

    public void deletePayment(Integer id) {
        archivePaymentRepository.deleteById(id);
    }

}
