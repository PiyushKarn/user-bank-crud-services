package com.project.first.userbankcrud.Domain;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Entity
@DynamicUpdate
@Table(name="users")
public class UserDomain {
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private Long phoneNumber;
    @Column
    private String address;
    @Column(columnDefinition = "TEXT")
    private String additionalDetailsUser;

//    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "userId")
//    private BankDomain bank;

    public UserDomain()
    {}

    public UserDomain(Long id, String name, Long phoneNumber, String address, String additionalDetailsUser) {
        this.id=id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.additionalDetailsUser = additionalDetailsUser;
    }

    public Long getId(){
        return id;
    }
    public void setId(Long id){
        this.id = id;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }

    public Long getPhoneNumber(){
        return phoneNumber;
    }
    public void setPhoneNumber(Long phoneNumber){
        this.phoneNumber=phoneNumber;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address=address;
    }

    public String getAdditionalDetailsUser() {
        return additionalDetailsUser;
    }

    public void setAdditionalDetailsUser(String additionalDetailsUser) {
        this.additionalDetailsUser = additionalDetailsUser;
    }

    @Override
    public String toString() {
        return "UserDomain{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", address='" + address + '\'' +
                ", additionalDetailsUser='" + additionalDetailsUser + '\'' +
                '}';
    }


}
