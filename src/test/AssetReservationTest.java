package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import dao.AssetManagementServiceImpl;
import java.time.LocalDate;

public class AssetReservationTest {

    @Test
    public void testReservation() throws Exception {
        AssetManagementServiceImpl service = new AssetManagementServiceImpl();
        int assetId = 1;
        int employeeId = 1;
        boolean result = service.reserveAsset(
            assetId, employeeId,
            LocalDate.now().toString(),
            LocalDate.now().plusDays(1).toString(),
            LocalDate.now().plusDays(3).toString()
        );
        assertTrue(result);
    }
}
