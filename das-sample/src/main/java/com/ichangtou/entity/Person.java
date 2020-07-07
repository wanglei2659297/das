package com.ichangtou.entity;

import java.sql.JDBCType;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.ppdai.das.client.ColumnDefinition;
import com.ppdai.das.client.TableDefinition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

/**
 * create by das-console
 * 请勿修改此文件
 */

@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public final class Person {

    public static final PersonDefinition PERSON = new PersonDefinition();

    public static class PersonDefinition extends TableDefinition {
            public final ColumnDefinition peopleid;
            public final ColumnDefinition name;
            public final ColumnDefinition cityid;
            public final ColumnDefinition provinceid;
            public final ColumnDefinition countryid;
            public final ColumnDefinition datachangeLasttime;
        
        public PersonDefinition as(String alias) {
            return _as(alias);
        }
        public PersonDefinition inShard(String shardId) {
            return _inShard(shardId);
        }

        public PersonDefinition shardBy(String shardValue) {
            return _shardBy(shardValue);
        }

        public PersonDefinition() {
            super("person");
					peopleid = column("PeopleID", JDBCType.INTEGER);
					name = column("Name", JDBCType.VARCHAR);
					cityid = column("CityID", JDBCType.INTEGER);
					provinceid = column("ProvinceID", JDBCType.INTEGER);
					countryid = column("CountryID", JDBCType.INTEGER);
					datachangeLasttime = column("DataChange_LastTime", JDBCType.TIMESTAMP);
		            setColumnDefinitions(
                        peopleid, name, cityid, provinceid, countryid, datachangeLasttime
            );
        }
    }


	@Id
	@Column(name = "PeopleID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer peopleid;

	@Column(name = "Name")
	private String name;

	@Column(name = "CityID")
	private Integer cityid;

	@Column(name = "ProvinceID")
	private Integer provinceid;

	@Column(name = "CountryID")
	private Integer countryid;

	@Column(name = "DataChange_LastTime")
	private Date datachangeLasttime;

	public Integer getPeopleID() {
		return peopleid;
	}

	public void setPeopleID(Integer aPeopleID) {
		this.peopleid = aPeopleID;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		this.name = aName;
	}

	public Integer getCityID() {
		return cityid;
	}

	public void setCityID(Integer aCityID) {
		this.cityid = aCityID;
	}

	public Integer getProvinceID() {
		return provinceid;
	}

	public void setProvinceID(Integer aProvinceID) {
		this.provinceid = aProvinceID;
	}

	public Integer getCountryID() {
		return countryid;
	}

	public void setCountryID(Integer aCountryID) {
		this.countryid = aCountryID;
	}

	public Date getDatachangeLasttime() {
		return datachangeLasttime;
	}

	public void setDatachangeLasttime(Date aDatachangeLasttime) {
		this.datachangeLasttime = aDatachangeLasttime;
	}

}