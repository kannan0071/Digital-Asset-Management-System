package test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import dao.AssetManagementServiceImpl;
import entity.Asset;
import java.time.LocalDate;

public class AssetCreationTest {

    @Test
    public void testAssetCreatedSuccessfully() {
        AssetManagementServiceImpl service = new AssetManagementServiceImpl();

        Asset asset = new Asset();
        asset.setName("Printer");
        asset.setType("Peripheral");
        asset.setSerialNumber("PR123");
        asset.setPurchaseDate(LocalDate.now());
        asset.setLocation("Admin Block");
        asset.setStatus("in use");
        asset.setOwnerId(1);
        assertTrue(service.addAsset(asset));
    }
}
