package tech.ada.extends_insights.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import tech.ada.extends_insights.domain.entities.Publication;
import tech.ada.extends_insights.domain.entities.Tag;
import tech.ada.extends_insights.domain.entities.User;
import tech.ada.extends_insights.domain.enums.Category;
import tech.ada.extends_insights.domain.models.requests.PublicationRequest;
import tech.ada.extends_insights.domain.models.requests.UpdatePublicationRequest;
import tech.ada.extends_insights.repository.PublicationRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PublicationServiceImplTest {

    public static final long PUBLICATION_ID = 1L;
    public static final String PUBLICATION_TITLE = "publicationTitle";
    public static final String PUBLICATION_CONTENT = "publicationContent";

    @InjectMocks
    private PublicationServiceImpl publicationService;
    @Mock
    private PublicationRepository publicationRepository;
    @Mock
    private ModelMapper modelMapper;

    private Publication publication;
    private PublicationRequest publicationRequest;
    private Optional<Publication> optionalPublication;
    private UpdatePublicationRequest updatePublicationRequest;
    private List<Publication> publicationList;
    private User user;
    private Category category;
    private Tag tag;
    private List<Tag> tagList;

    @BeforeEach
    void setUp() {
        publicationService = new PublicationServiceImpl(publicationRepository, modelMapper);
        publication = new Publication(PUBLICATION_ID, PUBLICATION_TITLE, PUBLICATION_CONTENT, user, category, tagList);
        optionalPublication = Optional.of(publication);
        publicationRequest = new PublicationRequest(PUBLICATION_TITLE, PUBLICATION_CONTENT, user, category, tagList);
        updatePublicationRequest = new UpdatePublicationRequest(PUBLICATION_TITLE, PUBLICATION_CONTENT, category, tagList);
        publicationList = List.of(publication);
        user = new User();
        category = Category.TECHNOLOGY;
        tag = new Tag("tagTitle");
        tagList = List.of(tag);
    }

    @DisplayName("Should create a new publication successfully")
    @Test
    void createPublication() {
        Mockito.when(modelMapper.map(publicationRequest, Publication.class)).thenReturn(publication);
        Mockito.when(publicationRepository.save(publication)).thenReturn(publication);
        Publication publication = publicationService.createPublication(publicationRequest);
        assertEquals(PUBLICATION_CONTENT, publication.getPublicationBody());
    }

    @DisplayName("Should read all publications successfully")
    @Test
    void readAllPublications() {
        Mockito.when(publicationRepository.findAll()).thenReturn(publicationList);
        List<Publication> publications = publicationService.readAllPublications();
        assertEquals(publicationList, publications);
    }

    @DisplayName("Should read publication by id successfully")
    @Test
    void readPublicationById() {
        Mockito.when(publicationRepository.findById(PUBLICATION_ID)).thenReturn(optionalPublication);
        Publication publicationById = publicationService.readPublicationById(PUBLICATION_ID);
        assertEquals(publicationById, publication);
    }

    @DisplayName("Should read publication by title successfully")
    @Test
    void readPublicationByTitle() {
        Mockito.when(publicationRepository.findByPublicationTitle(PUBLICATION_TITLE)).thenReturn(publicationList);
        List<Publication> publicationByTitle = publicationService.readPublicationByTitle(PUBLICATION_TITLE);
        assertEquals(publicationList, publicationByTitle);
    }

    @DisplayName("Should get publication by category successfully")
    @Test
    void getPublicationByCategory() {
        Mockito.when(publicationRepository.findByCategory(category)).thenReturn(publicationList);
        List<Publication> publicationByCategory = publicationService.getPublicationByCategory(category);
        assertEquals(publicationList, publicationByCategory);
    }

    @DisplayName("Should get publication by tag successfully")
    @Test
    void getPublicationByTag() {
        Mockito.when(publicationRepository.findByTags(tag)).thenReturn(publicationList);
        List<Publication> publicationByTag = publicationService.getPublicationByTag(tag);
        assertEquals(publicationList, publicationByTag);
    }

//    @DisplayName("Should get publication by user successfully")
//    @Test
//    void getPublicationByUser() {
//        Mockito.when(publicationRepository.findByAuthor(user)).thenReturn(publicationList);
//        List<Publication> publicationByUser = publicationService.getPublicationByUser(user);
//        assertEquals(publicationList, publicationByUser);
//    }

    @DisplayName("Should update publication successfully")
    @Test
    void updatePublication() {
        Mockito.when(publicationRepository.findById(PUBLICATION_ID)).thenReturn(optionalPublication);
        Mockito.when(publicationRepository.save(publication)).thenReturn(publication);
        Publication updatePublication = publicationService.updatePublication(PUBLICATION_ID, updatePublicationRequest);
        assertEquals(publication, updatePublication);
    }

    @DisplayName("Should delete publication successfully")
    @Test
    void deletePublication() {
        Mockito.when(publicationRepository.findById(PUBLICATION_ID)).thenReturn(optionalPublication);
        publicationService.deletePublication(PUBLICATION_ID);
        Mockito.verify(publicationRepository).delete(publication);
    }
}