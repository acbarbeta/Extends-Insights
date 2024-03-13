package tech.ada.extends_insights.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.models.requests.TagRequest;
import tech.ada.extends_insights.domain.models.requests.UpdateTagRequest;
import tech.ada.extends_insights.service.impl.TagServiceImpl;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class TagControllerTest {

    private MockMvc mockMvc;

    @Mock
    private TagServiceImpl tagService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private TagController tagController;

    private UpdateTagRequest updateTagRequest;

    private Tag tag;
    private TagRequest tagRequest;
    private List<Tag> tagList;
    private Publication publication;

    @BeforeEach
    public void setup() {
        //Arrange
        tag = new Tag(1L, "tagTitle", publication);
        tagRequest = new TagRequest("tagTitle");
        updateTagRequest = new UpdateTagRequest("newTagTitle");
        tagList = List.of(tag);
        publication = new Publication();
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    public void createTagHttpTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tagRequest)))
                .andExpect(status().isCreated());
        verify(tagService, times(1)).createTag(any());
    }

    @Test
    void getAllTags() throws Exception {
        when(tagService.readAllTags()).thenReturn(tagList);
        mockMvc.perform(MockMvcRequestBuilders.get("/tags/tags/get-all")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
        .andExpect(jsonPath("$.[0].title", equalTo("tagTitle")));
    }

//TODO: MUDANÇAS FEITAS EM 12/03 E COM ERROS - GETTAGSBYPUBLICATION E UPDATETAGHTTPTEST
    @Test
    void getTagsByPublication() throws Exception {
        when(tagService.readTagsByPublication(publication)).thenReturn(tagList);
        mockMvc.perform(MockMvcRequestBuilders.get("/tags/{publicationId}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.[0].id", equalTo(1)));
    }

    @Test
    void updateTagHttpTest() throws Exception {
        when(tagService.updateTag(anyLong(), any())).thenReturn(tag);
        mockMvc.perform(MockMvcRequestBuilders.patch("/tags/tags/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updateTagRequest)))
                        .andExpect(status().isOk());
        verify(tagService, times(1)).updateTag(anyLong(), any());
    }

    @Test
    void deleteTagHttpTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tags/tags/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tag)))
                        .andExpect(status().isNoContent());
        verify(tagService, times(1)).deleteTag(anyLong());
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static String asJsonString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}