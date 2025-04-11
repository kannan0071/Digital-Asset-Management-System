package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import entity.Asset;
import myexceptions.AssetNotFoundException;
import myexceptions.AssetNotMaintainException;
import util.DBConnection;

public class AssetManagementServiceImpl implements AssetManagementService{
	
	private Connection con;
	
	public AssetManagementServiceImpl() {
		// TODO Auto-generated constructor stub
		con = DBConnection.getConnection();
	}

	@Override
	public boolean addAsset(Asset asset) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = con.prepareStatement("insert into assets(name, type, serial_number, purchase_date, location, status, owner_id) values (?, ?, ?, ?, ?, ?, ?)");
			pstmt.setString(1, asset.getName());
			pstmt.setString(2, asset.getType());
			pstmt.setString(3, asset.getSerialNumber());
			pstmt.setDate(4, Date.valueOf(asset.getPurchaseDate()));
			pstmt.setString(5, asset.getLocation());
			pstmt.setString(6, asset.getStatus());
			pstmt.setInt(7, asset.getOwnerId());
			//System.out.println("Asset added successfully.");
			return pstmt.executeUpdate() > 0; 
		} catch (SQLException e) {
			System.err.println("Error adding asset: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean updateAsset(Asset asset) throws AssetNotFoundException{
		// TODO Auto-generated method stub
		try {
			PreparedStatement checkStmt = con.prepareStatement("SELECT * FROM assets WHERE asset_id = ?");
		    checkStmt.setInt(1, asset.getAssetId());
		    ResultSet rs = checkStmt.executeQuery();
		    if (!rs.next()) {
		    	throw new AssetNotFoundException("Asset with ID " + asset.getAssetId() + " not found.");
		    }
			
			PreparedStatement pstmt = con.prepareStatement("update assets set name = ?, type = ?, serial_number = ?, purchase_date = ?, location = ?, status = ?, owner_id = ? where asset_id = ?");
			pstmt.setString(1, asset.getName());
			pstmt.setString(2, asset.getType());
			pstmt.setString(3, asset.getSerialNumber());
			pstmt.setDate(4, Date.valueOf(asset.getPurchaseDate()));
			pstmt.setString(5, asset.getLocation());
			pstmt.setString(6, asset.getStatus());
			pstmt.setInt(7, asset.getOwnerId());
			pstmt.setInt(8, asset.getAssetId());
			//System.out.println("Asset updated successfully.");
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error updating asset: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean deleteAsset(int assetId) throws AssetNotFoundException{
		// TODO Auto-generated method stub
		try {
			PreparedStatement checkStmt = con.prepareStatement("select * from assets where asset_id = ?");
	        checkStmt.setInt(1, assetId);
	        ResultSet rs = checkStmt.executeQuery();
	        if (!rs.next()) {
	            throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
	        }
	        
			PreparedStatement pstmt = con.prepareStatement("delete from assets where asset_id = ?");
			pstmt.setInt(1, assetId);
			pstmt.executeUpdate();
			//System.out.println("Asset deleted successfully.");
//			int rowsAffected = pstmt.executeUpdate();
//			if (rowsAffected == 0) {
//	            throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
//	        }
			return true;
		} catch (SQLException e) {
			System.err.println("Error deleting asset: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean allocateAsset(int assetId, int employeeId, String allocationDate) throws AssetNotMaintainException{
		// TODO Auto-generated method stub
		try {
			// Check if asset has been maintained in last 2 years
	        PreparedStatement checkStmt = con.prepareStatement("select max(maintenance_date) from maintenance_records where asset_id = ?");
	        checkStmt.setInt(1, assetId);
	        ResultSet rs = checkStmt.executeQuery();
	        if (rs.next()) {
	            Date lastMaintenance = rs.getDate(1);
	            if (lastMaintenance == null || lastMaintenance.toLocalDate().isBefore(LocalDate.now().minusYears(2))) {
	                throw new AssetNotMaintainException("Asset with ID " + assetId + " hasn't been maintained in 2 years.");
	            }
	        }
			PreparedStatement pstmt = con.prepareStatement("insert into asset_allocations(asset_id, employee_id, allocation_date) values (?, ?, ?)");
			pstmt.setInt(1, assetId);
			pstmt.setInt(2, employeeId);
			pstmt.setDate(3, Date.valueOf(allocationDate));
			//System.out.println("Asset allocated successfully.");
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error allocating asset: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean deallocateAsset(int assetId, int employeeId, String returnDate) throws AssetNotFoundException{
		// TODO Auto-generated method stub
		try {
			PreparedStatement checkStmt = con.prepareStatement("select * from asset_allocations where asset_id = ? and employee_id = ?");
	        checkStmt.setInt(1, assetId);
	        checkStmt.setInt(2, employeeId);
	        ResultSet rs = checkStmt.executeQuery();
	        if (!rs.next()) {
	        	throw new AssetNotFoundException("No allocation found for Asset ID " + assetId + " and Employee ID " + employeeId);
	        }
			
			PreparedStatement pstmt = con.prepareStatement("update asset_allocations set return_date = ? where asset_id = ? and employee_id = ?");
			pstmt.setDate(1, Date.valueOf(returnDate));
			pstmt.setInt(2, assetId);
			pstmt.setInt(3, employeeId);
			//System.out.println("Asset deallocated successfully.");
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error deallocating asset: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean performMaintenance(int assetId, String maintenanceDate, String description, double cost) throws AssetNotFoundException{
		// TODO Auto-generated method stub
		try {
			PreparedStatement checkStmt = con.prepareStatement("select * from assets where asset_id = ?");
	        checkStmt.setInt(1, assetId);
	        ResultSet rs = checkStmt.executeQuery();
	        if (!rs.next()) {
	        	throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
	        }
			
			PreparedStatement pstmt = con.prepareStatement("insert into maintenance_records(asset_id, maintenance_date, description, cost) values (?, ?, ?, ?)");
			pstmt.setInt(1, assetId);
			pstmt.setDate(2, Date.valueOf(maintenanceDate));
			pstmt.setString(3, description);
			pstmt.setDouble(4, cost);
			//System.out.println("Maintenance record added successfully.");
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error performing maintenance: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean reserveAsset(int assetId, int employeeId, String reservationDate, String startDate, String endDate) throws AssetNotFoundException{
		// TODO Auto-generated method stub
		try {
			PreparedStatement checkStmt = con.prepareStatement("select * from assets where asset_id = ?");
	        checkStmt.setInt(1, assetId);
	        ResultSet rs = checkStmt.executeQuery();
	        if (!rs.next()) {
	            throw new AssetNotFoundException("Asset with ID " + assetId + " not found.");
	        }
			
			PreparedStatement pstmt = con.prepareStatement("insert into reservations(asset_id, employee_id, reservation_date, start_date, end_date) values (?, ?, ?, ?, ?)");
			pstmt.setInt(1, assetId);
			pstmt.setInt(2, employeeId);
			pstmt.setDate(3, Date.valueOf(reservationDate));
			pstmt.setDate(4, Date.valueOf(startDate));
			pstmt.setDate(5, Date.valueOf(endDate));
			//System.out.println("Asset reserved successfully.");
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error reserving asset: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean withdrawReservation(int reservationId) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement pstmt = con.prepareStatement("update reservations set status = ? where reservation_id = ?");
			pstmt.setString(1, "canceled");
			pstmt.setInt(2, reservationId);
			//System.out.println("Reservation withdrawn successfully.");
			return pstmt.executeUpdate() > 0;
		} catch (SQLException e) {
			System.err.println("Error withdrawing reservation: " + e.getMessage());
		}
		return false;
	}

}
