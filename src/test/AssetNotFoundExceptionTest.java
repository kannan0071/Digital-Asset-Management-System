package test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import dao.AssetManagementServiceImpl;
import myexceptions.AssetNotFoundException;

public class AssetNotFoundExceptionTest {

    @Test
    public void testAssetNotFound() {
        AssetManagementServiceImpl service = new AssetManagementServiceImpl();
        assertThrows(AssetNotFoundException.class, () -> {
            service.performMaintenance(9999, "2024-04-11", "Invalid asset", 100.0);
        });
    }
}
