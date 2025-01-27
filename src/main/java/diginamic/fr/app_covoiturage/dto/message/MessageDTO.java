package diginamic.fr.app_covoiturage.dto.message;

import java.time.LocalDateTime;
import java.util.List;

/**
 * The MessageDTO class is a data transfer object representing a message entity.
 * It is used to transfer message-related data between different layers of the application.
 */
public class MessageDTO {
    private int id;
    private String content;
    private LocalDateTime date;
    private boolean isRead;
    private boolean isDeleted;
    private List<Integer> employeeIds;

    public MessageDTO() {
    }

    public MessageDTO(int id, String content, LocalDateTime date, boolean isRead, boolean isDeleted,
            List<Integer> employeeIds) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.isRead = isRead;
        this.isDeleted = isDeleted;
        this.employeeIds = employeeIds;

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public List<Integer> getEmployeeIds() {
        return employeeIds;
    }

    public void setEmployeeIds(List<Integer> employeeIds) {
        this.employeeIds = employeeIds;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

}
