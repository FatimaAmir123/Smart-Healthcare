import java.util.*;
import Admin.*;
import System.*;

public class TestShiftChange {
    public static void main(String[] args) {
        // Clear existing
        for (Object[] d : DoctorRosterStore.getAllDoctors()) {
            DoctorRosterStore.removeDoctor((String)d[0]);
        }
        
        HospitalAdmin.preseedRosterStore();
        
        // Get Haris Bilal's ID
        String harisId = DoctorRosterStore.getDoctorIdByName("Haris Bilal");
        System.out.println("Haris Bilal ID: " + harisId);
        
        // Get his current shift ranges
        List<String> oldRanges = DoctorRosterStore.getShiftRanges(harisId);
        System.out.println("Old shift ranges: " + oldRanges);
        
        // Get his expanded time slots BEFORE
        List<String> oldSlots = DoctorRosterStore.getExpandedTimeSlots(harisId);
        System.out.println("Old expanded slots count: " + oldSlots.size());
        System.out.println("First 3 old slots: " + oldSlots.subList(0, Math.min(3, oldSlots.size())));
        
        // Simulate shift change: clear all ranges and add new ones
        DoctorRosterStore.clearShiftRanges(harisId);
        DoctorRosterStore.addShiftRange(harisId, "2pm to 5pm");
        DoctorRosterStore.addShiftRange(harisId, "6pm to 8pm");
        
        // Get his shift ranges AFTER
        List<String> newRanges = DoctorRosterStore.getShiftRanges(harisId);
        System.out.println("\nNew shift ranges: " + newRanges);
        
        // Get his expanded time slots AFTER
        List<String> newSlots = DoctorRosterStore.getExpandedTimeSlots(harisId);
        System.out.println("New expanded slots count: " + newSlots.size());
        System.out.println("First 3 new slots: " + newSlots.subList(0, Math.min(3, newSlots.size())));
        
        // Check all doctors
        System.out.println("\n--- All doctors and their shift ranges ---");
        for (Object[] d : DoctorRosterStore.getAllDoctors()) {
            String id = (String)d[0];
            String name = (String)d[1];
            List<String> ranges = DoctorRosterStore.getShiftRanges(id);
            System.out.println(id + " | " + name + " | shifts: " + ranges);
        }
        
        // Verify the change
        boolean pass = newRanges.size() == 2 
            && newRanges.contains("2pm to 5pm") 
            && newRanges.contains("6pm to 8pm")
            && !newRanges.contains("8 am to 11 am")
            && !newRanges.contains("12 pm to 2 pm");
        
        System.out.println("\n" + (pass ? "PASS - Shifts updated correctly" : "FAIL - Shifts not updated correctly"));
    }
}
