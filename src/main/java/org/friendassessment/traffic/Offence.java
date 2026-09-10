package org.friendassessment.traffic; import java.time.LocalDateTime;
public record Offence(String eventId,String vehicleNumber,OffenceType type,String location,LocalDateTime timestamp,double speed,double allowedSpeed,Severity severity){
 public Offence(String eventId,String vehicleNumber,OffenceType type,String location,LocalDateTime timestamp,double speed,double allowedSpeed){
  this(eventId,vehicleNumber,type,location,timestamp,speed,allowedSpeed,determine(type,speed,allowedSpeed));
 }
 private static Severity determine(OffenceType type,double speed,double allowed){
  if(eventIdInvalid(type,speed,allowed))throw new IllegalArgumentException("Invalid violation information");
  if(type!=OffenceType.OVER_SPEEDING)return Severity.MEDIUM;double excess=speed-allowed;if(excess<=10)return Severity.LOW;if(excess<=30)return Severity.MEDIUM;return Severity.HIGH;
 }
 private static boolean eventIdInvalid(OffenceType t,double s,double a){return t==null||s<0||a<0||(t==OffenceType.OVER_SPEEDING&&s<=a);}
 public Offence {if(eventId==null||eventId.isBlank()||vehicleNumber==null||vehicleNumber.isBlank()||type==null||location==null||location.isBlank()||timestamp==null)throw new IllegalArgumentException("Invalid violation information");vehicleNumber=vehicleNumber.trim().toUpperCase();}
}
