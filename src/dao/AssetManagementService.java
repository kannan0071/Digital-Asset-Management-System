package dao;

import entity.Asset;
import myexceptions.AssetNotFoundException;
import myexceptions.AssetNotMaintainException;

public interface AssetManagementService {
	 
    boolean addAsset(Asset asset);

    boolean updateAsset(Asset asset) throws AssetNotFoundException;

    boolean deleteAsset(int assetId) throws AssetNotFoundException;

    boolean allocateAsset(int assetId, int employeeId, String allocationDate)throws AssetNotMaintainException;

    boolean deallocateAsset(int assetId, int employeeId, String returnDate)throws AssetNotFoundException;

    boolean performMaintenance(int assetId, String maintenanceDate, String description, double cost)throws AssetNotFoundException;

    boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate)throws AssetNotFoundException;

    boolean withdrawReservation(int reservationId);
}
