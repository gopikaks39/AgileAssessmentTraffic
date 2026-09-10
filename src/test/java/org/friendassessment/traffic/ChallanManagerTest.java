package org.friendassessment.traffic;
import java.time.LocalDateTime;import org.junit.jupiter.api.Test;import static org.junit.jupiter.api.Assertions.*;
class ChallanManagerTest{
 private RegisteredVehicle vehicle(){return new RegisteredVehicle("TN02XY9876","Friend User",VehicleKind.CAR);}
 private Offence speeding(String id,double speed){return new Offence(id,"TN02XY9876",OffenceType.OVER_SPEEDING,"Road",LocalDateTime.now(),speed,60);}
 // POSITIVE TESTS
 @Test void positiveRegistration(){ChallanManager m=new ChallanManager();m.register(vehicle());assertEquals(RiskBand.SAFE,m.classify("TN02XY9876"));}
 @Test void positiveGenerateChallan(){ChallanManager m=new ChallanManager();m.register(vehicle());EChallan c=m.record(speeding("EV1",70));assertEquals(PaymentState.UNPAID,c.state());assertEquals(1000,c.amount());}
 @Test void positiveBoundaryOneOver(){ChallanManager m=new ChallanManager();m.register(vehicle());EChallan c=m.record(speeding("EV1",61));assertEquals(Severity.LOW,c.offence().severity());}
 @Test void positiveRepeatPenalty(){ChallanManager m=new ChallanManager();m.register(vehicle());double first=m.record(speeding("EV1",70)).amount();double second=m.record(speeding("EV2",70)).amount();assertTrue(second>first);assertEquals(1500,second);}
 @Test void positivePayment(){ChallanManager m=new ChallanManager();m.register(vehicle());EChallan c=m.record(speeding("EV1",70));assertEquals(1000,m.outstanding("TN02XY9876"));m.pay(c.id());assertEquals(PaymentState.PAID,c.state());assertEquals(0,m.outstanding("TN02XY9876"));}
 @Test void positiveRiskClassification(){ChallanManager m=new ChallanManager();m.register(vehicle());m.record(speeding("E1",70));m.record(speeding("E2",70));m.record(speeding("E3",70));assertEquals(RiskBand.HIGH_RISK,m.classify("TN02XY9876"));}
 // NEGATIVE TESTS
 @Test void negativeDuplicateVehicle(){ChallanManager m=new ChallanManager();m.register(vehicle());assertThrows(TrafficException.class,()->m.register(vehicle()));}
 @Test void negativeUnregisteredVehicle(){ChallanManager m=new ChallanManager();assertThrows(TrafficException.class,()->m.record(speeding("E1",70)));}
 @Test void negativeDuplicateEvent(){ChallanManager m=new ChallanManager();m.register(vehicle());m.record(speeding("E1",70));assertThrows(TrafficException.class,()->m.record(speeding("E1",70)));}
 @Test void negativeEqualSpeed(){assertThrows(IllegalArgumentException.class,()->speeding("E1",60));}
 @Test void negativeNegativeSpeed(){assertThrows(IllegalArgumentException.class,()->speeding("E1",-1));}
 @Test void negativeDoublePayment(){ChallanManager m=new ChallanManager();m.register(vehicle());EChallan c=m.record(speeding("E1",70));m.pay(c.id());assertThrows(TrafficException.class,()->m.pay(c.id()));}
 @Test void negativeMissingChallan(){ChallanManager m=new ChallanManager();assertThrows(TrafficException.class,()->m.pay("UNKNOWN"));}
 @Test void negativeInvalidVehicle(){assertThrows(IllegalArgumentException.class,()->new RegisteredVehicle("","",null));}
}
