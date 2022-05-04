package com.xl.commons.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class BaseModel implements Serializable {
	@Id
	@Column(name="lId")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long lId;//主键
}
