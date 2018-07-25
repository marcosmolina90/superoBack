package br.com.supero.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.supero.model.enums.EnumStatusTask;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the via_transporte database table.
 *
 */
@Entity
@Table
@Data
@EqualsAndHashCode(callSuper = false, of = "id")
public class Task extends BaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	private long codigo;

	@Column(nullable = false)
	private String descricao;

	@Enumerated(EnumType.STRING)
	private EnumStatusTask status;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtConclusao;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dtInclusao;

	

}