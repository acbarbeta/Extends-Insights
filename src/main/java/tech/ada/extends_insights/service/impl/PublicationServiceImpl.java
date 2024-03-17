package tech.ada.extends_insights.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.models.requests.PublicationRequest;
import tech.ada.extends_insights.domain.models.requests.UpdatePublicationRequest;
import tech.ada.extends_insights.repository.PublicationRepository;
import tech.ada.extends_insights.service.PublicationService;
import tech.ada.extends_insights.service.exceptions.ObjectNotFoundException;

import java.util.List;
import java.util.Optional;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;

    private final ModelMapper modelMapper;

    @Autowired
    public PublicationServiceImpl(PublicationRepository publicationRepository, ModelMapper modelMapper) {
        this.publicationRepository = publicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Publication createPublication(PublicationRequest request) {
        Publication convertedPublication = modelMapper.map(request, Publication.class);
        return publicationRepository.save(convertedPublication);
    }

    @Override
    public List<Publication> readAllPublications() {
        Optional<List<Publication>> optionalPublications = Optional.ofNullable(publicationRepository.findAll());
        return optionalPublications.orElseThrow(() -> new ObjectNotFoundException("Publications not found"));
    }

    @Override
    public Publication readPublicationById(Long id) {
        Optional<Publication> optionalPublication = publicationRepository.findById(id);
        return optionalPublication.orElseThrow(() -> new ObjectNotFoundException("Publication not found"));
    }

    @Override
    public List<Publication> readPublicationByTitle(String title) {
        var publicationByTitle = publicationRepository.findByPublicationTitle(title);
        if(publicationByTitle == null) {
            return null;
        }
        return publicationByTitle;
    }

    @Override
    public List<Publication> getPublicationByCategory(Category category) {
        var publicationByCategory = publicationRepository.findByCategory(category);
        if(publicationByCategory == null) {
            return null;
        }
        return publicationByCategory;
    }

    @Override
    public List<Publication> getPublicationByTag(Tag tag) {
        var publicationByTag = publicationRepository.findByTags(tag);
        if(publicationByTag == null) {
            return null;
        }
        return publicationByTag;
    }

    @Override
    public List<Publication> getPublicationByUser(Long userId) {
        var publicationByUser = publicationRepository.findByAuthor(userId);
        if(publicationByUser == null) {
            return null;
        }
        return publicationByUser;
    }

    @Override
    public Publication updatePublication(Long id, UpdatePublicationRequest updatePublicationRequest) {
        Optional<Publication> optionalPublication = publicationRepository.findById(id);

        if (optionalPublication.isPresent()) {
            Publication publication = optionalPublication.get();
            publication.setPublicationTitle(updatePublicationRequest.getTitle());
            publication.setPublicationBody(updatePublicationRequest.getContent());
            publication.setCategory(updatePublicationRequest.getCategory());
            publication.setTags(updatePublicationRequest.getTags());
            publicationRepository.save(publication);
            return publication;
        }
        return null;
    }


    @Override
    public void deletePublication(Long id) {
        Optional<Publication> publicationOptional = publicationRepository.findById(id);
        if (publicationOptional.isPresent()) {
            publicationRepository.delete(publicationOptional.get());
            ResponseEntity.noContent().build();
        } else {
            ResponseEntity.notFound().build();
        }
    }
}
