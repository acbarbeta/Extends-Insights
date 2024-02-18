package tech.ada.extends_insights.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.enums.Tag;
import tech.ada.extends_insights.domain.models.requests.CreatePublicationRequest;
import tech.ada.extends_insights.domain.models.requests.UpdatePublicationRequest;
import tech.ada.extends_insights.repository.PublicationRepository;

import java.util.List;
import java.util.Optional;

@RestController("/publications")
public class PublicationController {
    private final PublicationRepository publicationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PublicationController(PublicationRepository publicationRepository, ModelMapper modelMapper) {
        this.publicationRepository = publicationRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/publications-items")
    public ResponseEntity<Publication> createPublication(@RequestBody CreatePublicationRequest request) {

        Publication convertedPublication = modelMapper.map(request, Publication.class);

        Publication newPublication = publicationRepository.save(convertedPublication);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPublication);
    }

    @GetMapping("/publications-items")
    public ResponseEntity<List<Publication>> getAllPublications() {
        List<Publication> allPublications = publicationRepository.findAll();
        return ResponseEntity.ok(allPublications);
    }

    @GetMapping(value = "/publications-items", params = {"title"})
    public ResponseEntity<List<Publication>> getPublicationByTitle(@RequestParam String title) {
        List<Publication> publicationByTitle = publicationRepository.findByTitle(title);
        if(publicationByTitle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByTitle);
    }

    @GetMapping(value = "/publications-items", params = {"category"})
    public ResponseEntity<List<Publication>> getPublicationByCategory(@RequestParam Category category) {
        List<Publication> publicationByCategory = publicationRepository.findByCategory(category);
        if(publicationByCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByCategory);
    }

    @GetMapping(value = "/publications-items", params = {"tag"})
    public ResponseEntity<List<Publication>> getPublicationByTag(@RequestParam Tag tag) {
        List<Publication> publicationByTag = publicationRepository.findByTag(tag);
        if(publicationByTag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByTag);
    }

    @GetMapping(value = "/publications-items", params = {"user"})
    public ResponseEntity<List<Publication>> getPublicationByUser(@RequestParam User user) {
        List<Publication> publicationByUser = publicationRepository.findByUser(user);
        if(publicationByUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByUser);
    }

    @PatchMapping("/publications-items/{id}")
    public ResponseEntity<Publication> updatePublication(
            @PathVariable Long id,
            @RequestBody UpdatePublicationRequest request) throws Exception {
        Optional<Publication> optionalPublication = publicationRepository.findById(id);
        if(optionalPublication.isPresent()) {
            Publication publication = optionalPublication.get();
            if(request.getTitle() != null) publication.setPublicationTitle(request.getTitle());
            if(request.getContent() != null) publication.setPublicationBody(request.getContent());
            if(request.getCategory() != null) publication.setCategories(request.getCategory());
            if(request.getTag() != null) publication.setTags(request.getTag());

            Publication updatedPublication = publicationRepository.save(publication);
            return ResponseEntity.ok(updatedPublication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
