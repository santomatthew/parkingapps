package com.lawencon.parking;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class App {

	public final List<String> licensePlate = new ArrayList<String>();
	public final List<String> vehicleTypes = new ArrayList<String>();
	public final List<String> parkingCode = new ArrayList<String>();
	public final List<String> parkingDateCheckIn = new ArrayList<String>();
	public final List<String> parkingDateCheckOut = new ArrayList<String>();
	public final List<String> parkingReport = new ArrayList<String>();

	public static void main(String[] args) {
		final App app = new App();
		app.start();
	}

	public void start() {
		showMenu();
	}

	public static int scannerInt(String question, int min, int max) {
		System.out.print(question);
		final Scanner scan = new Scanner(System.in);

		try {
			final String inputChecker = scan.nextLine().trim();
			if (inputChecker.equals("")) {
				System.out.println("Invalid Input");
				return scannerInt(question, min, max);

			} else {
				final int input = Integer.parseInt(inputChecker);
				if (input >= min && input <= max) {
					return input;
				} else if (input < min) {
					System.out.println("Inputan tidak boleh lebih kecil dari 1");
					return scannerInt(question, min, max);
				} else if (input > max) {
					System.out.println("Input melebihi batas");
					return scannerInt(question, min, max);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			return scannerInt(question, min, max);
		}

		return scannerInt(question, min, max);
	}

	public static String scannerStr(String question, int min, int max, int type) {
		System.out.print(question);
		final Scanner scan = new Scanner(System.in);
		final String name = scan.nextLine().trim().toUpperCase();

		if (type == 1) {
			if (name.equals("")) {
				System.out.println("Invalid Input");
				return scannerStr(question, min, max, type);
			} else if (name.equals("B")) {
				return name;
			} else {
				System.out.println("Jenis kendaraan tidak sesuai dan anda tidak bisa parkir");
				final App app = new App();
				app.showMenu();
			}
		} else if (type == 2) {
			final String[] lastDigitPlateToArr = name.split("");
			if (lastDigitPlateToArr.length > 3) {
				System.out.println("Huruf maksimal 3 digit");
				return scannerStr(question, min, max, type);
			} else {
				boolean checker = checkIfAlphabet(lastDigitPlateToArr);
				if (checker) {
					return name;
				} else {
					System.out.println("Inputan harus huruf");
					return scannerStr(question, min, max, type);
				}
			}
		} else if (type == 3) {
			return name;
			// type == 4 digunakan jika kehilangan tiket dan ingin menginputkan plat
		} else if (type == 4) {
			if (name.equals("")) {
				System.out.println("Invalid Input");
				return scannerStr(question, min, max, type);
			} else if (name.equals("B")) {
				return name;
			}
		}
		return scannerStr(question, min, max, type);

	}

	public static boolean checkIfAlphabet(String[] nama) {
		boolean isAlphabet = false;
		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		final String[] alphabetArr = alphabet.split("");
		for (int i = 0; i < nama.length; i++) {
			isAlphabet = false;
			for (int j = 0; j < alphabetArr.length; j++) {
				if (nama[i].equals(alphabetArr[j])) {
					isAlphabet = true;
				}
			}
			if (!isAlphabet) {
				return isAlphabet;
			}
		}
		return isAlphabet;
	}

	public void showMenu() {
		System.out.println("1. Checkin Kendaraan \n2. Checkout Kendaraan \n3. Laporan  \n4. Keluar Aplikasi");
		final int chooseMenu = scannerInt("Pilih Menu ? ", 1, 4);
		chosenMenu(chooseMenu);
	}

	public void chosenMenu(int chooseMenu) {
		if (chooseMenu == 1) {
			showCheckInMenu();
		} else if (chooseMenu == 2) {
			showCheckOutMenu();
		} else if (chooseMenu == 3) {
			showParkingReport();
		} else if (chooseMenu == 4) {
			System.out.println("Kamu keluar aplikasi");
		}
	}

	public void showCheckInMenu() {
		final int vehicleType = scannerInt("1. Motor \n2. Mobil \nPilih tipe kendaraan = ", 1, 2);
		final String firstDigitPlate = scannerStr("Masukkan digit pertama  = ", 1, 1, 1);
		final int secondDigitPlate = scannerInt("Masukkan digit kedua = ", 1, 9999);
		final String thirdDigitPlate = scannerStr("Masukkan digit ketiga = ", 1, 3, 2);
		checkIn(vehicleType, firstDigitPlate, secondDigitPlate, thirdDigitPlate);
	}

	public void checkIn(int vehicleType,String firstDigitPlate, int secondDigitPlate, String thirdDigitPlate) {
		
		final VehicleRules[] listType = VehicleRules.values();
		final String vehicleName = listType[vehicleType-1].name;
		vehicleTypes.add(vehicleName);
		final String plate = firstDigitPlate + " " + secondDigitPlate + " " + thirdDigitPlate;
		final String parkingIdentity = getParkingCode();
		licensePlate.add(plate);
		parkingCode.add(parkingIdentity);
		String checkInDate = addParkingDate();
		parkingDateCheckIn.add(checkInDate);
		System.out.println("Kode parkir anda = " + parkingIdentity + " || Terima kasih telah checkin " + plate);
		String checkInReport = "Kode Parkir : "+ parkingIdentity + " || Jenis Kendaraan : "+ vehicleName + " || Plat Nomor : "+ plate + " || Jam : " + checkInDate + " || Tipe : Checkin";
		parkingReport.add(checkInReport);
		showMenu();
	}

	public String addParkingDate() {
		final Date dateNow = new Date();
		final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm a yyyy-MM-dd");
		final String dateNowStr = formatter.format(dateNow);
		return dateNowStr;
	}

	public String getParkingCode() {
		String randomCode = "";
		final String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ123456789";
		final StringBuilder sb = new StringBuilder();
		final Random random = new Random();
		final int length = 5;

		for (int i = 0; i < length; i++) {
			final int index = random.nextInt(alphabet.length());
			final char randomChar = alphabet.charAt(index);
			sb.append(randomChar);
		}
		randomCode = sb.toString();
		return randomCode;
	}

	public void showCheckOutMenu() {
		final String codeParking = scannerStr("Silahkan input kode parkir = ", 5, 5, 3);
		checkOut(codeParking);
	}

	public void checkOut(String code) {
		boolean isCheckedIn = false;
		int takeField = 0;
		final VehicleRules[] listType = VehicleRules.values();
		for (int i = 0; i < parkingCode.size(); i++) {
			if (code.equals(parkingCode.get(i))) {
				takeField = i;
				isCheckedIn = true;
				licensePlate.remove(i);
				String parkingDate = addParkingDate();
				parkingDateCheckOut.add(parkingDate);
			}
		}

		if (isCheckedIn) {
			String vehicleType = vehicleTypes.get(takeField);
			countGrandTotal(false, vehicleType);
		} else {
			final int option = scannerInt(
					"Kode parkir salah mau coba lagi? \n(1 Jika ya, 2 Jika ingin menggunakan plat nomor,3 Jika ingin kembali ke menu)",
					1, 3);
			wrongCodeOption(option);
		}

	}

	public void wrongCodeOption(int option) {
		if (option == 1) {
			showCheckOutMenu();
		} else if (option == 2) {
			showInputPlate();
		} else if (option == 3) {
			showMenu();
		}
	}

	public void showInputPlate() {

		final String firstDigitPlate = scannerStr("Masukkan digit pertama  = ", 1, 1, 4);
		final int secondDigitPlate = scannerInt("Masukkan digit kedua = ", 1, 9999);
		final String thirdDigitPlate = scannerStr("Masukkan digit ketiga = ", 1, 3, 2);
		final String licensePlateFull = firstDigitPlate + " " + secondDigitPlate + " " + thirdDigitPlate;
		checkLicensePlate(licensePlateFull);

	}

	public void checkLicensePlate(String plate) {
		boolean plateChecker = false;
		int takeField = 0;
		for (int i = 0; i < licensePlate.size(); i++) {
			if (plate.equals(licensePlate.get(i))) {
				takeField = i;
				plateChecker = true;
				licensePlate.remove(i);
				String parkingDate = addParkingDate();
				parkingDateCheckOut.add(parkingDate);
			}
		}

		if (plateChecker) {
			String vehicleType = vehicleTypes.get(takeField);

			countGrandTotal(true, vehicleType);
		} else {
			System.out.println("Kendaraan tidak terdaftar");
		}

	}

	public void countGrandTotal(boolean isPenalty, String vehicleType) {
		System.out.println("Anda berhasil checkout");
		final VehicleRules[] listType = VehicleRules.values();
		int penaltyPrice = 0;
		int pricePerHour = 0;
		for (int i = 0; i < listType.length; i++) {
			if (vehicleType.equals(listType[i].name)) {
				penaltyPrice = listType[i].pricePerHour;
				pricePerHour = listType[i].lostTicketPrice;
			}
		}

		int hourTotal = getParkingDuration();
		int priceTimesHour = pricePerHour * hourTotal;

		if (isPenalty) {
			System.out.println("Namun dikenakan denda sebesar Rp. " + penaltyPrice);
			System.out.println("Anda Parkir selama " + hourTotal + " dengan biaya Rp. ");
		} else {
			System.out.println("Total yang harus dibayar adalah");
		}

	}

	public int getParkingDuration() {
		int duration = 0;

		return duration;
	}
	
	public void showParkingReport() {
		
		System.out.println("========== Parking Report ==========");
		for(int i=0;i<parkingReport.size();i++) {
			System.out.println(parkingReport.get(i));
		}

		showMenu();
	}

}

enum VehicleRules {
	VEHICLE1("Motor", 1000, 50000), VEHICLE2("Mobil", 1000, 50000);

	public final String name;
	public final int pricePerHour;
	public final int lostTicketPrice;

	VehicleRules(String name, int pricePerHour, int lostTicketPrice) {
		this.name = name;
		this.pricePerHour = pricePerHour;
		this.lostTicketPrice = lostTicketPrice;
	}

}
