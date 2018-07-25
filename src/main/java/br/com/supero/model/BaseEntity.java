package br.com.supero.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlTransient;

@MappedSuperclass
@Table
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	@Version
	private Integer version;
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date createdat;
	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date lastupdate;

	@Transient
	private String erro;

	@XmlTransient
	public Date getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Date lastupdate) {
		this.lastupdate = lastupdate;
	}

	@XmlTransient
	public Date getCreatedat() {
		return createdat;
	}

	public void setCreatedat(Date createdat) {
		this.createdat = createdat;
	}

	@XmlTransient
	public Integer getVersion() {
		return version;
	}

	@XmlTransient
	public boolean isNew() {
		return getVersion() == null;
	}

	@PrePersist
	@PreUpdate
	private void setDateValues() {
		if (isNew()) {
			createdat = Calendar.getInstance().getTime();
			lastupdate = Calendar.getInstance().getTime();
		} else {
			lastupdate = Calendar.getInstance().getTime();
		}
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	

}