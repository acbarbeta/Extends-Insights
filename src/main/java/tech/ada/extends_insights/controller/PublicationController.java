package tech.ada.extends_insights.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.models.requests.PublicationRequest;
import tech.ada.extends_insights.domain.models.requests.UpdatePublicationRequest;
import tech.ada.extends_insights.service.PublicationService;

import java.util.List;

@RestController("/publications")
public class PublicationController {
    private final PublicationService publicationService;
    private final ModelMapper modelMapper;

    @Autowired
    public PublicationController(PublicationService publicationService, ModelMapper modelMapper) {
        this.publicationService = publicationService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Create a new publication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publication created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping("/publications-items")
    public ResponseEntity<Publication> createPublication(@RequestBody PublicationRequest request) {
        Publication newPublication = publicationService.createPublication(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newPublication);
    }

    @Operation(summary = "Get all publications")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publications found"),
            @ApiResponse(responseCode = "404", description = "Publications not found")
    })
    @GetMapping("/publications-items")
    public ResponseEntity<List<Publication>> getAllPublications() {
        return ResponseEntity.ok().body(publicationService.readAllPublications());
    }

    @Operation(summary = "Get publication by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication found"),
            @ApiResponse(responseCode = "404", description = "Publication not found")
    })
    @GetMapping("/publications-items/{id}")
    public ResponseEntity<Publication> getPublicationById(@PathVariable Long id) {
        return ResponseEntity.ok().body(publicationService.readPublicationById(id));
    }

    @Operation(summary = "Get publication by title")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication found"),
            @ApiResponse(responseCode = "404", description = "Publication not found")
    })
    @GetMapping(value = "/publications-items", params = {"title"})
    public ResponseEntity<List<Publication>> getPublicationByTitle(@RequestParam String title) {
        List<Publication> publicationByTitle = publicationService.readPublicationByTitle(title);
        if(publicationByTitle == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByTitle);
    }

    @Operation(summary = "Get publication by category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication found"),
            @ApiResponse(responseCode = "404", description = "Publication not found")
    })
    @GetMapping(value = "/publications-items", params = {"category"})
    public ResponseEntity<List<Publication>> getPublicationByCategory(@RequestParam Category category) {
        List<Publication> publicationByCategory = publicationService.getPublicationByCategory(category);
        if(publicationByCategory == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByCategory);
    }

    @Operation(summary = "Get publication by tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication found"),
            @ApiResponse(responseCode = "404", description = "Publication not found")
    })
    @GetMapping(value = "/publications-items", params = {"tag"})
    public ResponseEntity<List<Publication>> getPublicationByTag(@RequestParam Tag tag) {
        List<Publication> publicationByTag = publicationService.getPublicationByTag(tag);
        if(publicationByTag == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByTag);
    }

    @Operation(summary = "Get publication by author")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication found"),
            @ApiResponse(responseCode = "404", description = "Publication not found")
    })
    @GetMapping(value = "/publications-items", params = {"author"})
    public ResponseEntity<List<Publication>> getPublicationByUser(@RequestParam User author) {
        List<Publication> publicationByUser = publicationService.getPublicationByUser(author);
        if(publicationByUser == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publicationByUser);
    }

    @Operation(summary = "Update publication by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication updated"),
            @ApiResponse(responseCode = "404", description = "Publication not found")
    })
    @PatchMapping("/publications-items/{id}")
    public ResponseEntity<Publication> updatePublication(
            @PathVariable Long id,
            @RequestBody UpdatePublicationRequest request) throws Exception {
        Publication updatedPublication = publicationService.updatePublication(id, request);
        if (updatedPublication != null) {
            return ResponseEntity.ok(updatedPublication);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete publication by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Publication deleted"),
            @ApiResponse(responseCode = "404", description = "Publication not found")
    })
    @DeleteMapping("/publications-items/{id}")
    public ResponseEntity<Void> deletePublication(@PathVariable Long id) {
        Publication publication = publicationService.readPublicationById(id);
        if (publication != null) {
            publicationService.deletePublication(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
