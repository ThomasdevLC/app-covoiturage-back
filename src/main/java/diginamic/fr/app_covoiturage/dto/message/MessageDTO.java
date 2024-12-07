package diginamic.fr.app_covoiturage.dto.message;

import java.time.LocalDateTime;
import java.util.List;

public class MessageDTO {
    private int id;
    private String content;
    private LocalDateTime date;
    private List<Integer> employeeIds;

    public MessageDTO() {
    }

    public MessageDTO(int id, String content, LocalDateTime date, List<Integer> employeeIds) {
        this.id = id;
        this.content = content;
        this.date = date;
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

}
