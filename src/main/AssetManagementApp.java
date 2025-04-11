package main;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.Scanner;

import dao.AssetManagementServiceImpl;
import entity.Asset;
import myexceptions.AssetNotFoundException;
import myexceptions.AssetNotMaintainException;
import util.DBConnection;

public class AssetManagementApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Connection con = DBConnection.getConnection();
		if(con != null) {
			System.out.println(con);
			System.out.println("Connected!");
		}
		else {
			System.out.println("Failed to connect");
		}
		
		Scanner sc = new Scanner(System.in);
        AssetManagementServiceImpl service = new AssetManagementServiceImpl();

        while (true) {
            System.out.println("--- Digital Asset Management System ---");
            System.out.println("1. Add Asset");
            System.out.println("2. Update Asset");
            System.out.println("3. Delete Asset");
            System.out.println("4. Allocate Asset");
            System.out.println("5. Deallocate Asset");
            System.out.println("6. Perform Maintenance");
            System.out.println("7. Reserve Asset");
            System.out.println("8. Withdraw Reservation");
            System.out.println("9. Exit");
            System.out.print("Provide Option:\t");

            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> {
                    Asset asset = new Asset();
                    System.out.print("Enter Name: ");
                    sc.nextLine();
                    asset.setName(sc.nextLine());
                    System.out.print("Enter Type: ");
                    asset.setType(sc.nextLine());
                    System.out.print("Enter Serial Number: ");
                    asset.setSerialNumber(sc.nextLine());
                    System.out.print("Enter Purchase Date (yyyy-mm-dd): ");
                    asset.setPurchaseDate(LocalDate.parse(sc.nextLine()));
                    System.out.print("Enter Location: ");
                    asset.setLocation(sc.nextLine());
                    System.out.print("Enter Status: ");
                    asset.setStatus(sc.nextLine());
                    System.out.print("Enter Owner Employee ID: ");
                    asset.setOwnerId(sc.nextInt());

                    if (service.addAsset(asset))
                        System.out.println("Asset added successfully.");
                    else
                        System.out.println("Failed to add asset.");
                }
                case 2 -> {
                	try {
                		Asset asset = new Asset();
                        System.out.print("Enter Asset ID to update: ");
                        asset.setAssetId(sc.nextInt());
                        sc.nextLine();
                        System.out.print("Enter New Name: ");
                        asset.setName(sc.nextLine());
                        System.out.print("Enter New Type: ");
                        asset.setType(sc.nextLine());
                        System.out.print("Enter New Serial Number: ");
                        asset.setSerialNumber(sc.nextLine());
                        System.out.print("Enter New Purchase Date (yyyy-mm-dd): ");
                        asset.setPurchaseDate(LocalDate.parse(sc.nextLine()));
                        System.out.print("Enter New Location: ");
                        asset.setLocation(sc.nextLine());
                        System.out.print("Enter New Status: ");
                        asset.setStatus(sc.nextLine());
                        System.out.print("Enter New Owner Employee ID: ");
                        asset.setOwnerId(sc.nextInt());

                        if (service.updateAsset(asset))
                            System.out.println("Asset updated successfully.");
                        else
                            System.out.println("Failed to update asset.");
                	}
                	catch (AssetNotFoundException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 3 -> {
                	try {
                		System.out.print("Enter Asset ID to delete: ");
                        int assetId = sc.nextInt();
                        sc.nextLine();
                        if (service.deleteAsset(assetId))
                            System.out.println("Asset deleted successfully.");
                        else
                            System.out.println("Failed to delete asset.");
                	}
                    catch (AssetNotFoundException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 4 -> {
                    try {
                        System.out.print("Enter Asset ID: ");
                        int assetId = sc.nextInt();
                        System.out.print("Enter Employee ID: ");
                        int empId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Allocation Date (yyyy-mm-dd): ");
                        String allocDate = sc.nextLine();

                        if (service.allocateAsset(assetId, empId, allocDate))
                            System.out.println("Asset allocated successfully.");
                        else
                            System.out.println("Failed to allocate asset.");
                    } catch (AssetNotMaintainException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 5 -> {
                    try {
                    	System.out.print("Enter Asset ID: ");
                        int assetId = sc.nextInt();
                        System.out.print("Enter Employee ID: ");
                        int empId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Return Date (yyyy-mm-dd): ");
                        String returnDate = sc.nextLine();

                        if (service.deallocateAsset(assetId, empId, returnDate))
                            System.out.println("Asset deallocated successfully.");
                        else
                            System.out.println("Failed to deallocate asset.");
                    }
                    catch (AssetNotFoundException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 6 -> {
                    try {
                        System.out.print("Enter Asset ID: ");
                        int assetId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Maintenance Date (yyyy-mm-dd): ");
                        String mDate = sc.nextLine();
                        System.out.print("Enter Description: ");
                        String desc = sc.nextLine();
                        System.out.print("Enter Cost: ");
                        double cost = sc.nextDouble();

                        if (service.performMaintenance(assetId, mDate, desc, cost))
                            System.out.println("Maintenance recorded successfully.");
                        else
                            System.out.println("Failed to record maintenance.");
                    } catch (AssetNotFoundException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 7 -> {
                    try {
                        System.out.print("Enter Asset ID: ");
                        int assetId = sc.nextInt();
                        System.out.print("Enter Employee ID: ");
                        int empId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Reservation Date (yyyy-mm-dd): ");
                        String resDate = sc.nextLine();
                        System.out.print("Enter Start Date (yyyy-mm-dd): ");
                        String startDate = sc.nextLine();
                        System.out.print("Enter End Date (yyyy-mm-dd): ");
                        String endDate = sc.nextLine();

                        if (service.reserveAsset(assetId, empId, resDate, startDate, endDate))
                            System.out.println("Reservation created successfully.");
                        else
                            System.out.println("Failed to reserve asset.");
                    } catch (AssetNotFoundException e) {
                        System.err.println("Error: " + e.getMessage());
                    }
                }
                case 8 -> {
                	System.out.print("Enter Reservation ID: ");
                    int resId = sc.nextInt();
                    sc.nextLine();

                    if (service.withdrawReservation(resId))
                        System.out.println("Reservation withdrawn successfully.");
                    else
                        System.out.println("Failed to withdraw reservation.");
                }
                case 9 -> {
                    System.out.println("Exiting Asset Management System...");
                    sc.close();
                    System.exit(0);
                }
                default -> throw new IllegalArgumentException("Invalid Option: " + choice);
            }
        }
	}

}
