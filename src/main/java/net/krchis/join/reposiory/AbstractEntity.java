package net.krchis.join.reposiory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AbstractEntity {
	
	@Id
	@GeneratedValue
	@JsonProperty
	private Long id;
	
	@CreatedDate
	private LocalDateTime createDate;
	
	@LastModifiedDate
	private LocalDateTime modifyDate;
	
	
	public String getCreateDateFormat() {
		return getDateFormat(createDate,"yyyy-MM-dd HH:mm:ss");
	}

	public String getModifyDateFormat() {
	
		return getDateFormat(modifyDate,"yyyy-MM-dd HH:mm:ss");

	}
	private String getDateFormat(LocalDateTime dateTime,String format) {
		if (dateTime == null) {
			return "";
		}
		return dateTime.format(DateTimeFormatter.ofPattern(format));
	}
	
	@Override
	public String toString() {
		return "AbstractEntity [id=" + id + ", createDate=" + createDate + ", modifyDate=" + modifyDate + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
    public Long getId() {
		return id;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractEntity other = (AbstractEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
