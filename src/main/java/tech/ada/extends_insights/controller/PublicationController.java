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
import tech.ada.extends_insights.domain.models.requests.PublicationRequest;
import tech.ada.extends_insights.repository.PublicationRepository;

import java.util.List;

@RestController("/publication")
public class PublicationController {
    private final PublicationRepository publicationRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PublicationController(PublicationRepository publicationRepository, ModelMapper modelMapper) {
        this.publicationRepository = publicationRepository;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/publication-item")
    public ResponseEntity<Publication> createPublication(@RequestBody PublicationRequest request) {

        Publication convertedPublication = modelMapper.map(request, Publication.class);

        Publication newPublication = publicationRepository.save(convertedPublication);

        return ResponseEntity.status(HttpStatus.CREATED).body(newPublication);
    }

    @GetMapping("/publication-item")
    public ResponseEntity<List<Publication>> getAllPublications() {
        List<Publication> allPublications = publicationRepository.findAll();
        return ResponseEntity.ok(allPublications);
    }

    @GetMapping(value = "/publication-item", params = {"title"})
    public ResponseEntity<List<Publication>> getPublicationByTitle(@RequestParam String title) {
        List<Publication> publicationByTitle = publicationRepository.findByTitle(title);
        return ResponseEntity.ok(publicationByTitle);
    }

    @GetMapping(value = "/publication-item", params = {"category"})
    public ResponseEntity<List<Publication>> getPublicationByCategory(@RequestParam Category category) {
        List<Publication> publicationByCategory = publicationRepository.findByCategory(category);
        return ResponseEntity.ok(publicationByCategory);
    }

    @GetMapping(value = "/publication-item", params = {"tag"})
    public ResponseEntity<List<Publication>> getPublicationByTag(@RequestParam Tag tag) {
        List<Publication> publicationByTag = publicationRepository.findByTag(tag);
        return ResponseEntity.ok(publicationByTag);
    }

    @GetMapping(value = "/publication-item", params = {"user"})
    public ResponseEntity<List<Publication>> getPublicationByUser(@RequestParam User user) {
        List<Publication> publicationByUser = publicationRepository.findByUser(user);
        return ResponseEntity.ok(publicationByUser);
    }
}
