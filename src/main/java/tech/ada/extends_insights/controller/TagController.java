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
import tech.ada.extends_insights.domain.models.requests.TagRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateTagRequest;
import tech.ada.extends_insights.service.TagService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;
    private final ModelMapper modelMapper;

    @Autowired
    public TagController(TagService tagService, ModelMapper modelMapper) {
        this.tagService = tagService;
        this.modelMapper = modelMapper;
    }

    @Operation(summary = "Create a new tag")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tag created"),
            @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<Tag> createTag(@RequestBody TagRequest request) {
        Tag newTag = tagService.createTag(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTag);
    }

    @Operation(summary = "Get all tags")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tags found"),
            @ApiResponse(responseCode = "404", description = "Tags not found")
    })
    @GetMapping("/tags/get-all")
    public ResponseEntity<List<Tag>> getAllTags() {
        return ResponseEntity.ok(tagService.readAllTags());
    }

    @Operation(summary = "Get tags by publication")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tags found"),
            @ApiResponse(responseCode = "404", description = "Tags not found")
    })
    @GetMapping(value = "/tags", params = {"publicationId"})
    public ResponseEntity<List<Tag>> getTagsByPublication(@RequestParam Publication publication) {
        List<Tag> tagsByPublication = tagService.readTagsByPublication(publication);
        if (tagsByPublication.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(tagsByPublication);
    }

    @Operation(summary = "Update tag by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tag updated"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @PatchMapping("/tags/{id}")
    public ResponseEntity<Tag> updateTag(@PathVariable Long id, @RequestBody UpdateTagRequest request) {
        Optional<Tag> optionalTag = tagService.readTagById(id);
        if (optionalTag.isPresent()) {
            Tag tag = optionalTag.get();
            tag.setTitle(request.getTitle());
            Tag updatedTag = tagService.updateTag(id, request);
            return ResponseEntity.ok(updatedTag);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Delete tag by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tag deleted"),
            @ApiResponse(responseCode = "404", description = "Tag not found")
    })
    @DeleteMapping("/tags/{id}")
    public ResponseEntity<Void> deleteTag(@PathVariable Long id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }
}
