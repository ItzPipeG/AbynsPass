package dev.itzpipeg.abynspass.models;

import dev.itzpipeg.abynspass.utils.MisionStatus;
import dev.itzpipeg.abynspass.utils.MisionType;
import org.bukkit.entity.Player;

import java.util.List;

public class ChallengeModel {

    private List<String> challengePlayerParticipants;
    private List<String> challengePlayerCompleted;
    private MisionType challengeType;
    private String challengeValue;
    private Integer challengeAmount;
    private Long challengeStartDate;
    private MisionStatus challengeStatus;

    public ChallengeModel(List<String> challengePlayerParticipants, List<String> challengePlayerCompleted, MisionType challengeType, String challengeValue, Integer challengeAmount, Long challengeStartDate, MisionStatus challengeStatus){
        this.challengePlayerParticipants = challengePlayerParticipants;
        this.challengePlayerCompleted = challengePlayerCompleted;
        this.challengeType = challengeType;
        this.challengeValue = challengeValue;
        this.challengeAmount = challengeAmount;
        this.challengeStartDate = challengeStartDate;
        this.challengeStatus = challengeStatus;
    }

    public List<String> getChallengePlayerParticipants(){
        return challengePlayerParticipants;
    }

    public void setChallengePlayerCompleted(List<String> challengePlayerCompleted){
        this.challengePlayerCompleted = challengePlayerCompleted;
    }

    public void addChallengePlayerCompleted(String player){
        this.challengePlayerCompleted.add(player);
    }

    public void removeChallengePlayerCompleted(String player){
        this.challengePlayerCompleted.remove(player);
    }


    public List<String> getChallengePlayerCompleted(){
        return challengePlayerCompleted;
    }

    public void setChallengePlayerParticipants(List<String> challengePlayerParticipants){
        this.challengePlayerParticipants = challengePlayerParticipants;
    }

    public void addChallengePlayerParticipants(String player){
        this.challengePlayerParticipants.add(player);
    }

    public void removeChallengePlayerParticipants(String player){
        this.challengePlayerParticipants.remove(player);
    }


    public MisionType getChallengeType(){
        return challengeType;
    }

    public void setChallengeType(MisionType challengeType){
        this.challengeType = challengeType;
    }

    public String getChallengeValue(){
        return challengeValue;
    }

    public void setChallengeValue(String challengeValue){
        this.challengeValue = challengeValue;
    }

    public Integer getChallengeAmount(){
        return challengeAmount;
    }

    public void setChallengeAmount(Integer challengeAmount){
        this.challengeAmount = challengeAmount;
    }

    public Long getChallengeStartDate(){
        return challengeStartDate;
    }

    public void setChallengeStartDate(Long challengeStartDate){
        this.challengeStartDate = challengeStartDate;
    }

    public MisionStatus getChallengeStatus(){
        return challengeStatus;
    }

    public void setChallengeStatus(MisionStatus challengeStatus){
        this.challengeStatus = challengeStatus;
    }
}
