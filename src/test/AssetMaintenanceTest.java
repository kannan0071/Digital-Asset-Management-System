package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import dao.AssetManagementServiceImpl;
import java.time.LocalDate;

public class AssetMaintenanceTest {

    @Test
    public void testMaintenanceAdded() throws Exception {
        AssetManagementServiceImpl service = new AssetManagementServiceImpl();
        int assetId = 1; // make sure this exists
        boolean result = service.performMaintenance(assetId, LocalDate.now().toString(), "Checkup", 150.0);
        assertTrue(result);
    }
}
