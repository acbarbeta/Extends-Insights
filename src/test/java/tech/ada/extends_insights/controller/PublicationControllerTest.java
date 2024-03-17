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
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.models.requests.UpdatePublicationRequest;
import tech.ada.extends_insights.service.impl.PublicationServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static tech.ada.extends_insights.service.impl.PublicationServiceImplTest.PUBLICATION_CONTENT;
import static tech.ada.extends_insights.service.impl.PublicationServiceImplTest.PUBLICATION_TITLE;

@ExtendWith(MockitoExtension.class)
class PublicationControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PublicationServiceImpl publicationService;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private PublicationController publicationController;

    private Publication publication;
    private List<Publication> publicationList;
    private UpdatePublicationRequest updatePublicationRequest;

    private User user;
    private Category category;
    private Tag tag;
    private List<Tag> tagList;

    @BeforeEach
    void setUp() {
        user = new User("usernameTest", "123456789", "email@test.com");
        category = Category.TECHNOLOGY;
        tagList = new ArrayList<>();
        tag = new Tag(1L, "tagTitle", publication);
        publication = new Publication(1L, "title", "content", user, category, tagList);
        updatePublicationRequest = new UpdatePublicationRequest(PUBLICATION_TITLE, PUBLICATION_CONTENT, category, tagList);
        publicationList = List.of(publication);
        mockMvc = MockMvcBuilders.standaloneSetup(publicationController).build();
    }


    @Test
    void createPublication() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/publications-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andExpect(status().isCreated());
        verify(publicationService, times(1)).createPublication(any());
    }

    @Test
    void getAllPublications() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService, times(1)).readAllPublications();
    }

    @Test
    void getPublicationById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items/" + anyLong())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService, times(1)).readPublicationById(anyLong());
    }

    @Test
    void getPublicationByTitle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items?title=" + anyString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService, times(1)).readPublicationByTitle(anyString());
    }

    @Test
    void getPublicationByCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items?category=" + category)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService, times(1)).getPublicationByCategory(category);
    }

    @Test
    void getPublicationByTag() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items?tag=" + any())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService, times(1)).getPublicationByTag(any());
    }

    @Test
    void getPublicationByUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/publications-items?author=" + user)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andDo(MockMvcResultHandlers.print());
        verify(publicationService, times(1)).getPublicationByUser(user);
    }

    @Test
    void updatePublication() throws Exception {
        when(publicationService.updatePublication(anyLong(), any())).thenReturn(publication);
        mockMvc.perform(MockMvcRequestBuilders.patch("/publications-items/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(updatePublicationRequest)))
                .andExpect(status().isOk());
        verify(publicationService, times(1)).updatePublication(anyLong(), any());
    }



    @Test
    public void deletePublicationHttpTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/publications-items/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(publication)))
                .andExpect(status().isNoContent());
        verify(publicationService, times(1)).deletePublication(anyLong());
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