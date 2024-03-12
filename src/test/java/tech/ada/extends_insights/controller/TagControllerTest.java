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

    private Tag tag;
    private TagRequest tagRequest;
    private List<Tag> tagList;
    private Publication publication;

    @BeforeEach
    public void setup() {
        //Arrange
        tag = new Tag("tagTitle");
        tagRequest = new TagRequest("tagTitle");
        tagList = List.of(tag);
        publication = new Publication();
        mockMvc = MockMvcBuilders.standaloneSetup(tagController).build();
    }

    @Test
    public void createTagHttpTest() throws Exception {
        when(tagService.createTag(tagRequest)).thenReturn(tag);
        var response = mockMvc.perform(MockMvcRequestBuilders.post("/tags/tags-creation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tagRequest)))
                .andExpect(status().isCreated());
        response.andExpect(jsonPath("$.title", equalTo(tag.getTitle())));

        //verify(tagService, times(1)).createTag(any());
    }

    @Test
    void getAllTags() throws Exception {
        when(tagService.readAllTags()).thenReturn(tagList);
        mockMvc.perform(MockMvcRequestBuilders.get("/tags/tags/get-all")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(tag)))
                .andDo(MockMvcResultHandlers.print());
        verify(tagService).readAllTags();
        verify(tagService, times(1)).readAllTags();
    }

    @Test
    void getTagsByPublication() throws Exception {
        when(tagService.readTagsByPublication(publication)).thenReturn(tagList);
        mockMvc.perform(MockMvcRequestBuilders.get("/tags/{publicationId}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                        .andDo(MockMvcResultHandlers.print());
        verify(tagService).readTagsByPublication(publication);
        verify(tagService, times(1)).readTagsByPublication(publication);
    }

    @Test
    void updateTag() throws Exception {
        when(tagService.updateTag(anyLong(), any())).thenReturn(tag);
        mockMvc.perform(MockMvcRequestBuilders.put("/tags/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(tagRequest)))
                .andExpect(status().isOk());
        verify(tagService, times(1)).updateTag(anyLong(), any());
    }

    @Test
    void deleteTag() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/tags/{id}", anyLong())
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