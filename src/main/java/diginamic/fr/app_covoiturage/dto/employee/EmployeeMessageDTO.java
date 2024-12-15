package diginamic.fr.app_covoiturage.dto.employee;

import java.util.List;

import diginamic.fr.app_covoiturage.dto.message.MessageDTO;

public class EmployeeMessageDTO {
    private int employeeId;
    private String firstName;
    private String lastName;
    private String email;
    private List<MessageDTO> messages;

    public EmployeeMessageDTO() {
    }

    public EmployeeMessageDTO(int employeeId, String firstName, String lastName, String email,
            List<MessageDTO> messages) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.messages = messages;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MessageDTO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDTO> messages) {
        this.messages = messages;
    }
}
