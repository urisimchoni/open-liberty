/*******************************************************************************
 * Copyright (c) 2022 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/

package dead.eentities;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;

/**
 * ppp>Entity of the Common Datamodel (which uses all the possible JPA 2.0 Annotations as described in the
 * <a href="http://www.j2ee.me/javaee/6/docs/api/javax/persistence/package-summary.html">javax.persistence documentation</a>)
 *
 *
 * <p>These annotations are exercised:
 * <ul>
 * <li><b>@Access</b> (01 of 65):
 * <p>The Access annotation is used to specify an access type to be applied to an entity class, mapped superclass, or embeddable class, or to a specific attribute of such a class.
 * <p>Possible @Access combinations:
 * <ul>
 * <li>ElementType.TYPE = Entity
 * <li>ElementType.TYPE = Mapped Superclass
 * <li>ElementType.TYPE = Embeddable
 * <li>AccessType.FIELD on class
 * <li>AccessType.PROPERTY on class
 * <li>AccessType.FIELD on field
 * <li>AccessType.PROPERTY on property
 * </ul>
 * <p>@Access combinations exercised in this entity:
 * <ul>
 * <li>ElementType.TYPE = Entity
 * <li>AccessType.FIELD on class
 * </ul>
 *
 *
 * <li><b>@Basic</b> (06 or 65):
 * <p>The Basic annotation is the simplest type of mapping to a database column. It is optional (usually for documentation puposes) and does not need to be used to make a field or
 * property persistent.
 * <p>Possible @Basic combinations:
 * <ul>
 * <li>FetchType.EAGER on field (to persist)
 * <li>FetchType.LAZY on field (to persist)
 * <li>FetchType.EAGER on property (to persist)
 * <li>FetchType.LAZY on property (to persist)
 * </ul>
 * <p>@Basic combinations exercised in this entity:
 * <ul>
 * <li>FetchType.EAGER on field
 * </ul>
 *
 *
 * <li><b>@Column</b> (07 of 65):
 * <p>Is used to specify a mapped column for a persistent property or field.
 * <p>Possible @Column combinations:
 * <ul>
 * <li>@Column on field
 * <li>@Column on property
 * <li>columnDefinition
 * <li>insertable
 * <li>length
 * <li>name
 * <li>nullable
 * <li>precision
 * <li>scale
 * <li>table
 * <li>updatable
 * </ul>
 * <p>@Column combinations exercised in this entity:
 * <ul>
 * <li>@Column on field
 * <li>columnDefinition
 * <li>insertable
 * <li>length
 * <li>name
 * <li>nullable
 * <li>precision
 * <li>scale
 * <li>table
 * <li>updatable
 * </ul>
 *
 *
 * <li><b>@Entity</b> (14 of 65):
 * <p>Specifies that the class is an entity.
 * <p>Possible @Entity combinations:
 * <ul>
 * <li>@Entity on top-level class
 * <li>@Entity on top-level class with name specified
 * </ul>
 * <p>@Entity combinations exercised in this entity:
 * <ul>
 * <li>@Entity on top-level class
 * </ul>
 *
 *
 * <li><b>@Id</b> (22 of 65):
 * <p>Specifies the primary key property or field of an entity
 * <p>These are the possible @Id combinations:
 * <ul>
 * <li>@Id on field
 * <li>@Id on property
 * </ul>
 * <p>These are the @Id combinations exercised in this entity:
 * <ul>
 * <li>@Id on field
 * </ul>
 *
 *
 * <li><b>@Inheritance</b> (24 of 65):
 * <p>Defines the inheritance strategy to be used for an entity class hierarchy.
 * <p>These are the possible @Inheritance combinations:
 * <ul>
 * <li>@Inheritance on Entity
 * <li>strategy=InheritanceType.JOINED
 * <li>strategy=InheritanceType.SINGLE_TABLE)
 * <li>strategy=InheritanceType.TABLE_PER_CLASS)
 * </ul>
 * <p>These are the @Inheritance combinations exercised in this entity:
 * <ul>
 * <li>@Inheritance on Entity
 * <li>strategy=InheritanceType.TABLE_PER_CLASS)
 * </ul>
 *
 *
 * <li><b>@OneToMany</b> (37 of 65):
 * <p>Defines a many-valued association with one-to-many multiplicity.
 * <p>@OneToMany combinations:
 * <ul>
 * <li>@OneToMany on field
 * <li>@OneToMany on property
 * <li>cascade
 * <li>fetch
 * <li>mappedBy
 * <li>orphanRemoval
 * <li>targetEntity
 * </ul>
 * <p>@OneToMany combinations exercised in this entity:
 * <ul>
 * <li>@OneToMany on field
 * <li>cascade
 * <li>fetch
 * <li>mappedBy
 * <li>orphanRemoval
 * <li>targetEntity
 * </ul>
 *
 *
 * <li><b>@Table</b> (60 of 65):
 * <p>This annotation specifies the primary table for the annotated entity.
 * <p>Possible @Table combinations:
 * <ul>
 * <li>@Table on Entity
 * <li>name
 * <li>catalog
 * <li>schema
 * <li>uniqueConstraints
 * </ul>
 * <p>@Table combinations exercised in this entity:
 * <ul>
 * <li>@Table on Entity
 * <li>name
 * <li>catalog
 * <li>schema
 * <li>uniqueConstraints
 * </ul>
 *
 *
 * <li><b>@Version</b> (65 of 65):
 * <p>This annotation specifies the version field or property of an entity class that serves as its optimistic lock value.
 * <p>@Version combinations:
 * <ul>
 * <li>@Version on field
 * <li>@Version on property
 * </ul>
 * <p>@Version combinations exercised in this entity:
 * <ul>
 * <li>@Version on field
 * </ul>
 * </ul>
 *
 *
 * <p><b>Notes:</b>
 * <ol>
 * <li>None
 * </ol>
 *
 *
 * <p><b>UML:</b>
 *
 * <pre>
 *
 *          +----------------------+                +----------------------+                +----------------------+
 *          |  EntityOneToMany02  |<-------------->|  EntityOneToMany02  |<-------------->|  EntityOneToMany01  |
 *          +----------------------+  n          n  +----------------------+  n          n  +----------------------+
 *
 * </pre>
 */
@Access(AccessType.FIELD)
//@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Table(name = "CDM_EntityOneToMany02")
//     catalog = "CDM_EntityOneToMany02_Catalog",
//     schema = "CMD_EntityOneToMany02_Schema",
//     uniqueConstraints=@UniqueConstraint(columnNames={"entity01_string01"}))
public class EntityOneToMany02 {

    @Basic(fetch = FetchType.LAZY)
    @Column(columnDefinition = "INTEGER",
            insertable = true,
            length = 15,
            name = "entityOneToMany02_id",
            nullable = true,
            precision = 15,
            scale = 15,
            table = "CDM_EntityOneToMany02",
            updatable = true)
    @Id
    private int entityOneToMany02_id;

    @Basic(fetch = FetchType.EAGER)
    @Column(columnDefinition = "INTEGER",
            insertable = true,
            length = 15,
            name = "entityOneToMany02_version",
            nullable = true,
            precision = 15,
            scale = 15,
            table = "CDM_EntityOneToMany02",
            updatable = true)
    @Version
    private int entityOneToMany02_version;

    //----------------------------------------------------------------------------------------------
    // @OneToMany combinations
    //----------------------------------------------------------------------------------------------
    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
               orphanRemoval = false)
    private Set entityOneToMany02_set01;

    @OneToMany(cascade = CascadeType.DETACH,
               fetch = FetchType.EAGER,
//             mappedBy = "entityOneToMany01_set02",
               orphanRemoval = true)
//             targetEntity = EntityOneToMany02.class)
    private Set entityOneToMany02_set02;

    @OneToMany(cascade = CascadeType.MERGE,
               fetch = FetchType.LAZY,
               orphanRemoval = false)
//             targetEntity = EntityOneToMany01.class)
    private Set entityOneToMany02_set03;

    @OneToMany(cascade = CascadeType.PERSIST,
               fetch = FetchType.EAGER,
//             mappedBy = "entityOneToMany01_set04",
               orphanRemoval = true)
    private Set<EntityOneToMany01> entityOneToMany02_set04;

    @OneToMany(cascade = CascadeType.REFRESH,
               fetch = FetchType.LAZY,
               orphanRemoval = false)
    private Set<EntityOneToMany01> entityOneToMany02_set05;

    @OneToMany(cascade = CascadeType.REMOVE,
               fetch = FetchType.EAGER,
//             mappedBy = "entityOneToMany01_set07",
               orphanRemoval = true)
    private Set<EntityOneToMany02> entityOneToMany02_set06;

    @OneToMany(cascade = { CascadeType.ALL, CascadeType.DETACH },
               fetch = FetchType.LAZY,
               orphanRemoval = false)
    private Set<EntityOneToMany02> entityOneToMany02_set07;

    @OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE },
               fetch = FetchType.EAGER,
               orphanRemoval = true)
    private List entityOneToMany02_list01;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST },
               fetch = FetchType.LAZY,
//             mappedBy = "entityOneToMany01_list02",
               orphanRemoval = false)
//             targetEntity = EntityOneToMany02.class)
    private List entityOneToMany02_list02;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH },
               fetch = FetchType.EAGER,
               orphanRemoval = true)
//             targetEntity = EntityOneToMany01.class)
    private List entityOneToMany02_list03;

    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE },
               fetch = FetchType.LAZY,
//             mappedBy = "entityOneToMany01_list04",
               orphanRemoval = false)
    private List<EntityOneToMany01> entityOneToMany02_list04;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.EAGER,
               orphanRemoval = true)
    private List<EntityOneToMany01> entityOneToMany02_list05;

    @OneToMany(cascade = CascadeType.DETACH,
               fetch = FetchType.LAZY,
//             mappedBy = "entityOneToMany01_list06",
               orphanRemoval = false)
    private List<EntityOneToMany02> entityOneToMany02_list06;

    @OneToMany(cascade = CascadeType.MERGE,
               fetch = FetchType.EAGER,
               orphanRemoval = true)
    private List<EntityOneToMany02> entityOneToMany02_list07;

    @OneToMany(cascade = CascadeType.PERSIST,
               fetch = FetchType.LAZY,
               orphanRemoval = false)
    private Collection entityOneToMany02_collection01;

    @OneToMany(cascade = CascadeType.REFRESH,
               fetch = FetchType.EAGER,
//             mappedBy = "entityOneToMany01_collection02",
               orphanRemoval = true)
//             targetEntity = EntityOneToMany02.class)
    private Collection entityOneToMany02_collection02;

    @OneToMany(cascade = CascadeType.REMOVE,
               fetch = FetchType.LAZY,
               orphanRemoval = false)
//             targetEntity = EntityOneToMany01.class)
    private Collection entityOneToMany02_collection03;

    @OneToMany(cascade = { CascadeType.ALL, CascadeType.DETACH },
               fetch = FetchType.EAGER,
//             mappedBy = "entityOneToMany01_collection04",
               orphanRemoval = true)
    private Collection<EntityOneToMany01> entityOneToMany02_collection04;

    @OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE },
               fetch = FetchType.LAZY,
               orphanRemoval = false)
    private Collection<EntityOneToMany01> entityOneToMany02_collection05;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST },
               fetch = FetchType.EAGER,
//             mappedBy = "entityOneToMany01_collection06",
               orphanRemoval = true)
    private Collection<EntityOneToMany02> entityOneToMany02_collection06;

    @OneToMany(cascade = { CascadeType.PERSIST, CascadeType.REFRESH },
               fetch = FetchType.LAZY,
               orphanRemoval = false)
    private Collection<EntityOneToMany02> entityOneToMany02_collection07;

    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE },
               fetch = FetchType.EAGER,
               orphanRemoval = true)
    private HashSet entityOneToMany02_hashset01;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.LAZY,
//             mappedBy = "entityOneToMany01_hashSet02",
               orphanRemoval = false)
//             targetEntity = EntityOneToMany02.class)
    private HashSet entityOneToMany02_hashset02;

    @OneToMany(cascade = CascadeType.DETACH,
               fetch = FetchType.EAGER,
               orphanRemoval = true)
//             targetEntity = EntityOneToMany01.class)
    private HashSet entityOneToMany02_hashset03;

    @OneToMany(cascade = CascadeType.MERGE,
               fetch = FetchType.LAZY,
//             mappedBy = "entityOneToMany01_hashSet04",
               orphanRemoval = false)
    private HashSet<EntityOneToMany01> entityOneToMany02_hashset04;

    @OneToMany(cascade = CascadeType.PERSIST,
               fetch = FetchType.EAGER,
               orphanRemoval = true)
    private HashSet<EntityOneToMany01> entityOneToMany02_hashset05;

    @OneToMany(cascade = CascadeType.REFRESH,
               fetch = FetchType.LAZY,
//             mappedBy = "entityOneToMany01_hashSet06",
               orphanRemoval = false)
    private HashSet<EntityOneToMany02> entityOneToMany02_hashset06;

    @OneToMany(cascade = CascadeType.REMOVE,
               fetch = FetchType.EAGER,
               orphanRemoval = true)
    private HashSet<EntityOneToMany02> entityOneToMany02_hashset07;

    @OneToMany(cascade = { CascadeType.ALL, CascadeType.DETACH },
               fetch = FetchType.LAZY,
               orphanRemoval = false)
    private Map entityOneToMany02_map01;

    @OneToMany(cascade = { CascadeType.DETACH, CascadeType.MERGE },
               fetch = FetchType.EAGER,
//             mappedBy = "entityOneToMany01_map02",
               orphanRemoval = true)
//             targetEntity = EntityOneToMany02.class)
    private Map entityOneToMany02_map02;

    @OneToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST },
               fetch = FetchType.LAZY,
               orphanRemoval = false)
//             targetEntity = EntityOneToMany01.class)
    private Map entityOneToMany02_map03;

//  @OneToMany(cascade={CascadeType.PERSIST, CascadeType.REFRESH},
//             fetch = FetchType.EAGER,
//             mappedBy = "entityOneToMany01_map04",
//             orphanRemoval = true)
//  private Map<EntityOneToMany01,EntityOneToMany01> entityOneToMany02_map04;

    @OneToMany(cascade = { CascadeType.REFRESH, CascadeType.REMOVE },
               fetch = FetchType.LAZY,
               orphanRemoval = false)
    private Map<EntityOneToMany01, EntityOneToMany01> entityOneToMany02_map05;

    @OneToMany(cascade = CascadeType.ALL,
               fetch = FetchType.EAGER,
//             mappedBy = "entityOneToMany01_map06",
               orphanRemoval = true)
    private Map<EntityOneToMany02, EntityOneToMany02> entityOneToMany02_map06;

    @OneToMany(cascade = CascadeType.DETACH,
               fetch = FetchType.LAZY,
               orphanRemoval = false)
    private Map<EntityOneToMany02, EntityOneToMany02> entityOneToMany02_map07;

    public EntityOneToMany02() {}

    public EntityOneToMany02(int id,
                             int version,
                             Set set01,
                             Set set02,
                             Set set03,
                             Set<EntityOneToMany01> set04,
                             Set<EntityOneToMany01> set05,
                             Set<EntityOneToMany02> set06,
                             Set<EntityOneToMany02> set07,
                             List list01,
                             List list02,
                             List list03,
                             List<EntityOneToMany01> list04,
                             List<EntityOneToMany01> list05,
                             List<EntityOneToMany02> list06,
                             List<EntityOneToMany02> list07,
                             Collection collection01,
                             Collection collection02,
                             Collection collection03,
                             Collection<EntityOneToMany01> collection04,
                             Collection<EntityOneToMany01> collection05,
                             Collection<EntityOneToMany02> collection06,
                             Collection<EntityOneToMany02> collection07,
                             HashSet hashset01,
                             HashSet hashset02,
                             HashSet hashset03,
                             HashSet<EntityOneToMany01> hashset04,
                             HashSet<EntityOneToMany01> hashset05,
                             HashSet<EntityOneToMany02> hashset06,
                             HashSet<EntityOneToMany02> hashset07,
                             Map map01,
                             Map map02,
                             Map map03,
//                           Map<EntityOneToMany01,EntityOneToMany01>   map04,
                             Map<EntityOneToMany01, EntityOneToMany01> map05,
                             Map<EntityOneToMany02, EntityOneToMany02> map06,
                             Map<EntityOneToMany02, EntityOneToMany02> map07) {
        this.entityOneToMany02_id = id;
        this.entityOneToMany02_version = version;
        this.entityOneToMany02_set01 = set01;
        this.entityOneToMany02_set02 = set02;
        this.entityOneToMany02_set03 = set03;
        this.entityOneToMany02_set04 = set04;
        this.entityOneToMany02_set05 = set05;
        this.entityOneToMany02_set06 = set06;
        this.entityOneToMany02_set07 = set07;
        this.entityOneToMany02_list01 = list01;
        this.entityOneToMany02_list02 = list02;
        this.entityOneToMany02_list03 = list03;
        this.entityOneToMany02_list04 = list04;
        this.entityOneToMany02_list05 = list05;
        this.entityOneToMany02_list06 = list06;
        this.entityOneToMany02_list07 = list07;
        this.entityOneToMany02_collection01 = collection01;
        this.entityOneToMany02_collection02 = collection02;
        this.entityOneToMany02_collection03 = collection03;
        this.entityOneToMany02_collection04 = collection04;
        this.entityOneToMany02_collection05 = collection05;
        this.entityOneToMany02_collection06 = collection06;
        this.entityOneToMany02_collection07 = collection07;
        this.entityOneToMany02_hashset01 = hashset01;
        this.entityOneToMany02_hashset02 = hashset02;
        this.entityOneToMany02_hashset03 = hashset03;
        this.entityOneToMany02_hashset04 = hashset04;
        this.entityOneToMany02_hashset05 = hashset05;
        this.entityOneToMany02_hashset06 = hashset06;
        this.entityOneToMany02_hashset07 = hashset07;
        this.entityOneToMany02_map01 = map01;
        this.entityOneToMany02_map02 = map02;
        this.entityOneToMany02_map03 = map03;
//      this.entityOneToMany02_map04        = map04;
        this.entityOneToMany02_map05 = map05;
        this.entityOneToMany02_map06 = map06;
        this.entityOneToMany02_map07 = map07;
    }

    @Override
    public String toString() {
        return (" EntityOneToMany02: " +
                " entityOneToMany02_id: " + getEntityOneToMany02_id() +
                " entityOneToMany02_version: " + getEntityOneToMany02_version() +
                " entityOneToMany02_set01: " + getEntityOneToMany02_set01() +
                " entityOneToMany02_set02: " + getEntityOneToMany02_set02() +
                " entityOneToMany02_set03: " + getEntityOneToMany02_set03() +
                " entityOneToMany02_set04: " + getEntityOneToMany02_set04() +
                " entityOneToMany02_set05: " + getEntityOneToMany02_set05() +
                " entityOneToMany02_set06: " + getEntityOneToMany02_set06() +
                " entityOneToMany02_set07: " + getEntityOneToMany02_set07() +
                " entityOneToMany02_list01: " + getEntityOneToMany02_list01() +
                " entityOneToMany02_list02: " + getEntityOneToMany02_list02() +
                " entityOneToMany02_list03: " + getEntityOneToMany02_list03() +
                " entityOneToMany02_list04: " + getEntityOneToMany02_list04() +
                " entityOneToMany02_list05: " + getEntityOneToMany02_list05() +
                " entityOneToMany02_list06: " + getEntityOneToMany02_list06() +
                " entityOneToMany02_list07: " + getEntityOneToMany02_list07() +
                " entityOneToMany02_collection01: " + getEntityOneToMany02_collection01() +
                " entityOneToMany02_collection02: " + getEntityOneToMany02_collection02() +
                " entityOneToMany02_collection03: " + getEntityOneToMany02_collection03() +
                " entityOneToMany02_collection04: " + getEntityOneToMany02_collection04() +
                " entityOneToMany02_collection05: " + getEntityOneToMany02_collection05() +
                " entityOneToMany02_collection06: " + getEntityOneToMany02_collection06() +
                " entityOneToMany02_collection07: " + getEntityOneToMany02_collection07() +
                " entityOneToMany02_hashset01: " + getEntityOneToMany02_hashset01() +
                " entityOneToMany02_hashset02: " + getEntityOneToMany02_hashset02() +
                " entityOneToMany02_hashset03: " + getEntityOneToMany02_hashset03() +
                " entityOneToMany02_hashset04: " + getEntityOneToMany02_hashset04() +
                " entityOneToMany02_hashset05: " + getEntityOneToMany02_hashset05() +
                " entityOneToMany02_hashset06: " + getEntityOneToMany02_hashset06() +
                " entityOneToMany02_hashset07: " + getEntityOneToMany02_hashset07() +
                " entityOneToMany02_map01: " + getEntityOneToMany02_map01() +
                " entityOneToMany02_map02: " + getEntityOneToMany02_map02() +
                " entityOneToMany02_map03: " + getEntityOneToMany02_map03() +
//              " entityOneToMany02_map04: "        + getEntityOneToMany02_map04() +
                " entityOneToMany02_map05: " + getEntityOneToMany02_map05() +
                " entityOneToMany02_map06: " + getEntityOneToMany02_map05() +
                " entityOneToMany02_map07: " + getEntityOneToMany02_map07());
    }

    //----------------------------------------------------------------------------------------------
    // Persisent property accessor(s)
    //----------------------------------------------------------------------------------------------
    public int getEntityOneToMany02_id() {
        return entityOneToMany02_id;
    }

    public void setEntityOneToMany02_id(int p) {
        this.entityOneToMany02_id = p;
    }

    public int getEntityOneToMany02_version() {
        return entityOneToMany02_version;
    }

    public void setEntityOneToMany02_version(int p) {
        this.entityOneToMany02_version = p;
    }

    public Set getEntityOneToMany02_set01() {
        return entityOneToMany02_set01;
    }

    public void setEntityOneToMany02_set01(Set p) {
        this.entityOneToMany02_set01 = p;
    }

    public Set getEntityOneToMany02_set02() {
        return entityOneToMany02_set01;
    }

    public void setEntityOneToMany02_set02(Set p) {
        this.entityOneToMany02_set01 = p;
    }

    public Set getEntityOneToMany02_set03() {
        return entityOneToMany02_set01;
    }

    public void setEntityOneToMany02_set03(Set p) {
        this.entityOneToMany02_set01 = p;
    }

    public Set<EntityOneToMany01> getEntityOneToMany02_set04() {
        return entityOneToMany02_set04;
    }

    public void setEntityOneToMany02_set04(Set<EntityOneToMany01> p) {
        this.entityOneToMany02_set04 = p;
    }

    public Set<EntityOneToMany01> getEntityOneToMany02_set05() {
        return entityOneToMany02_set05;
    }

    public void setEntityOneToMany02_set05(Set<EntityOneToMany01> p) {
        this.entityOneToMany02_set05 = p;
    }

    public Set<EntityOneToMany02> getEntityOneToMany02_set06() {
        return entityOneToMany02_set06;
    }

    public void setEntityOneToMany02_set06(Set<EntityOneToMany02> p) {
        this.entityOneToMany02_set06 = p;
    }

    public Set<EntityOneToMany02> getEntityOneToMany02_set07() {
        return entityOneToMany02_set07;
    }

    public void setEntityOneToMany02_set07(Set<EntityOneToMany02> p) {
        this.entityOneToMany02_set07 = p;
    }

    public List getEntityOneToMany02_list01() {
        return entityOneToMany02_list01;
    }

    public void setEntityOneToMany02_list01(List p) {
        this.entityOneToMany02_list01 = p;
    }

    public List getEntityOneToMany02_list02() {
        return entityOneToMany02_list02;
    }

    public void setEntityOneToMany02_list02(List p) {
        this.entityOneToMany02_list02 = p;
    }

    public List getEntityOneToMany02_list03() {
        return entityOneToMany02_list03;
    }

    public void setEntityOneToMany02_list03(List p) {
        this.entityOneToMany02_list03 = p;
    }

    public List<EntityOneToMany01> getEntityOneToMany02_list04() {
        return entityOneToMany02_list04;
    }

    public void setEntityOneToMany02_list04(List<EntityOneToMany01> p) {
        this.entityOneToMany02_list04 = p;
    }

    public List<EntityOneToMany01> getEntityOneToMany02_list05() {
        return entityOneToMany02_list05;
    }

    public void setEntityOneToMany02_list05(List<EntityOneToMany01> p) {
        this.entityOneToMany02_list05 = p;
    }

    public List<EntityOneToMany02> getEntityOneToMany02_list06() {
        return entityOneToMany02_list06;
    }

    public void setEntityOneToMany02_list06(List<EntityOneToMany02> p) {
        this.entityOneToMany02_list06 = p;
    }

    public List<EntityOneToMany02> getEntityOneToMany02_list07() {
        return entityOneToMany02_list07;
    }

    public void setEntityOneToMany02_list07(List<EntityOneToMany02> p) {
        this.entityOneToMany02_list07 = p;
    }

    public Collection getEntityOneToMany02_collection01() {
        return entityOneToMany02_collection01;
    }

    public void setEntityOneToMany02_collection01(Collection p) {
        this.entityOneToMany02_collection01 = p;
    }

    public Collection getEntityOneToMany02_collection02() {
        return entityOneToMany02_collection02;
    }

    public void setEntityOneToMany02_collection02(Collection p) {
        this.entityOneToMany02_collection02 = p;
    }

    public Collection getEntityOneToMany02_collection03() {
        return entityOneToMany02_collection03;
    }

    public void setEntityOneToMany02_collection03(Collection p) {
        this.entityOneToMany02_collection03 = p;
    }

    public Collection<EntityOneToMany01> getEntityOneToMany02_collection04() {
        return entityOneToMany02_collection04;
    }

    public void setEntityOneToMany02_collection04(Collection<EntityOneToMany01> p) {
        this.entityOneToMany02_collection04 = p;
    }

    public Collection<EntityOneToMany01> getEntityOneToMany02_collection05() {
        return entityOneToMany02_collection05;
    }

    public void setEntityOneToMany02_collection05(Collection<EntityOneToMany01> p) {
        this.entityOneToMany02_collection05 = p;
    }

    public Collection<EntityOneToMany02> getEntityOneToMany02_collection06() {
        return entityOneToMany02_collection06;
    }

    public void setEntityOneToMany02_collection06(Collection<EntityOneToMany02> p) {
        this.entityOneToMany02_collection06 = p;
    }

    public Collection<EntityOneToMany02> getEntityOneToMany02_collection07() {
        return entityOneToMany02_collection07;
    }

    public void setEntityOneToMany02_collection07(Collection<EntityOneToMany02> p) {
        this.entityOneToMany02_collection07 = p;
    }

    public HashSet getEntityOneToMany02_hashset01() {
        return entityOneToMany02_hashset01;
    }

    public void setEntityOneToMany02_hashset01(HashSet p) {
        this.entityOneToMany02_hashset01 = p;
    }

    public HashSet getEntityOneToMany02_hashset02() {
        return entityOneToMany02_hashset02;
    }

    public void setEntityOneToMany02_hashset02(HashSet p) {
        this.entityOneToMany02_hashset02 = p;
    }

    public HashSet getEntityOneToMany02_hashset03() {
        return entityOneToMany02_hashset03;
    }

    public void setEntityOneToMany02_hashset03(HashSet p) {
        this.entityOneToMany02_hashset03 = p;
    }

    public HashSet<EntityOneToMany01> getEntityOneToMany02_hashset04() {
        return entityOneToMany02_hashset04;
    }

    public void setEntityOneToMany02_hashset04(HashSet<EntityOneToMany01> p) {
        this.entityOneToMany02_hashset04 = p;
    }

    public HashSet<EntityOneToMany01> getEntityOneToMany02_hashset05() {
        return entityOneToMany02_hashset05;
    }

    public void setEntityOneToMany02_hashset05(HashSet<EntityOneToMany01> p) {
        this.entityOneToMany02_hashset05 = p;
    }

    public HashSet<EntityOneToMany02> getEntityOneToMany02_hashset06() {
        return entityOneToMany02_hashset06;
    }

    public void setEntityOneToMany02_hashset06(HashSet<EntityOneToMany02> p) {
        this.entityOneToMany02_hashset06 = p;
    }

    public HashSet<EntityOneToMany02> getEntityOneToMany02_hashset07() {
        return entityOneToMany02_hashset07;
    }

    public void setEntityOneToMany02_hashset07(HashSet<EntityOneToMany02> p) {
        this.entityOneToMany02_hashset07 = p;
    }

    public Map getEntityOneToMany02_map01() {
        return entityOneToMany02_map01;
    }

    public void setEntityOneToMany02_map01(Map p) {
        this.entityOneToMany02_map01 = p;
    }

    public Map getEntityOneToMany02_map02() {
        return entityOneToMany02_map02;
    }

    public void setEntityOneToMany02_map02(Map p) {
        this.entityOneToMany02_map02 = p;
    }

    public Map getEntityOneToMany02_map03() {
        return entityOneToMany02_map03;
    }

    public void setEntityOneToMany02_map03(Map p) {
        this.entityOneToMany02_map03 = p;
    }

//  public Map<EntityOneToMany01,EntityOneToMany01> getEntityOneToMany02_map04() {
//      return entityOneToMany02_map04;
//  }
//  public void setEntityOneToMany02_map04(Map<EntityOneToMany01,EntityOneToMany01> p) {
//      this.entityOneToMany02_map04 = p;
//  }

    public Map<EntityOneToMany01, EntityOneToMany01> getEntityOneToMany02_map05() {
        return entityOneToMany02_map05;
    }

    public void setEntityOneToMany02_map05(Map<EntityOneToMany01, EntityOneToMany01> p) {
        this.entityOneToMany02_map05 = p;
    }

    public Map<EntityOneToMany02, EntityOneToMany02> getEntityOneToMany02_map06() {
        return entityOneToMany02_map06;
    }

    public void setEntityOneToMany02_map06(Map<EntityOneToMany02, EntityOneToMany02> p) {
        this.entityOneToMany02_map06 = p;
    }

    public Map<EntityOneToMany02, EntityOneToMany02> getEntityOneToMany02_map07() {
        return entityOneToMany02_map07;
    }

    public void setEntityOneToMany02_map07(Map<EntityOneToMany02, EntityOneToMany02> p) {
        this.entityOneToMany02_map07 = p;
    }
}
