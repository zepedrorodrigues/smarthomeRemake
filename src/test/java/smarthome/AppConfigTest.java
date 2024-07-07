package smarthome;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for AppConfig
 */
class AppConfigTest {

    /**
     * Test if the method filePathModels returns the correct path
     */
    @Test
    void testFilePathModelsReturnCorrectPath() {
        //Arrange
        String expected = "configModels.properties";
        AppConfig appConfig = new AppConfig();

        //Act
        String result = appConfig.filePathModels();

        //Assert
        assertEquals(expected, result,
                "filePathModels should return 'configModels.properties'");
    }

    /**
     * Test if the method filePathDelta returns the correct path
     */
    @Test
    void testFilePathDeltaReturnsCorrectPath() {
        //Arrange
        String expected = "configDelta.properties";
        AppConfig appConfig = new AppConfig();

        //Act
        String result = appConfig.filePathDelta();

        //Assert
        assertEquals(expected, result,
                "filePathDelta should return 'configDelta.properties'");
    }
}