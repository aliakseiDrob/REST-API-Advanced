package com.epam.esm.modelcreator;

import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.service.GiftCertificateService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@RequiredArgsConstructor
public class CertificateCollectionModelCreator {

    private static final int FIRST_PAGE = 1;
    private static final int SECOND_PAGE = 2;
    private final GiftCertificateService service;

    public CollectionModel<CertificateDto> createModel(List<CertificateDto> certificates, int page, int items) {
        certificates.forEach(this::addLinkWithItself);
        CollectionModel<CertificateDto> model = CollectionModel.of(certificates, linkTo(methodOn(GiftCertificateController.class)
                .getAll(page, items)).withSelfRel());
        addPaginateLinks(model, page, items);
        return model;
    }

    private void addLinkWithItself(CertificateDto certificate) {
        certificate.add(linkTo(methodOn(GiftCertificateController.class).getGiftCertificate(certificate.getId())).withSelfRel());
    }

    private void addPaginateLinks(CollectionModel<CertificateDto> model, int page, int items) {
        addLinksPreviousPages(model, page, items);
        addLinkToNextPages(model, page, items);
    }

    private void addLinksPreviousPages(CollectionModel<CertificateDto> model, int page, int size) {
        if (page > FIRST_PAGE) {
            model.add(linkTo(methodOn(GiftCertificateController.class).getAll(1, size)).withRel("firstPage"));
            if (page > SECOND_PAGE) {
                model.add(linkTo(methodOn(GiftCertificateController.class).getAll(page - 1, size)).withRel("previousPage"));
            }
        }
    }

    private void addLinkToNextPages(CollectionModel<CertificateDto> model, int page, int items) {
        long tagsQuantity = service.getRowCounts();
        if (tagsQuantity > page * items) {
            model.add(linkTo(methodOn(GiftCertificateController.class).getAll(page + 1, items)).withRel("nextPage"));
            if (tagsQuantity > (page + 1) * items) {
                int lastPage = (int) Math.ceil(tagsQuantity / (double) items);
                model.add(linkTo(methodOn(GiftCertificateController.class).getAll(lastPage, items)).withRel("lastPage"));
            }
        }
    }
}
