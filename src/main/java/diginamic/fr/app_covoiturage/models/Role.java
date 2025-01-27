package diginamic.fr.app_covoiturage.models;

import java.util.HashSet;
import java.util.Set;

import diginamic.fr.app_covoiturage.models.enums.RoleName;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

/**
 * Represents a role in the application. A role is associated with a specific
 * set of permissions and can be assigned to one or more employees. This class
 * is mapped to the "roles" table in the database.
 *
 * Fields:
 * - id: Represents the unique identifier of the role, automatically generated.
 * - roleName: Represents the name of the role, stored as a string and must be unique.
 * - employees: Represents the collection of employees associated with this role.
 *
 * Relationships:
 * - Many-to-Many relationship with the Employee entity, where the "employee_roles"
 *   join table persists the association between roles and employees.
 *
 * This entity supports operations like assigning a role name, getting the associated
 * employees, and retrieving the role's unique identifier.
 */
@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @JoinTable(name = "employee_roles", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "employee_id ", referencedColumnName = "id"))
    @Column(name = "role_name", nullable = false, unique = true)
    private RoleName roleName;

    @ManyToMany(mappedBy = "roles")
    private Set<Employee> employees = new HashSet<>();

    public Role() {
    }

    public Role(RoleName roleName) {
        this.roleName = roleName;
    }

    public RoleName getRoleName() {
        return roleName;
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setRoleName(RoleName roleName) {
        this.roleName = roleName;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
