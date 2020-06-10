package iaf.ofek.hadracha.base_course.web_server.Data;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class InMemoryMapDataBaseTest {

    private class FakeEntity1 implements Entity<FakeEntity1> {
        private int id;
        public int data;

        @Override
        public int getId() {
            return id;
        }

        @Override
        public void setId(int id) {
            this.id = id;
        }

        @Override
        public FakeEntity1 clone() {
            try {
                return (FakeEntity1) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    private class FakeEntity2 implements Entity<FakeEntity2> {
        private int id;
        public int data;

        @Override
        public int getId() {
            return id;
        }

        @Override
        public void setId(int id) {
            this.id = id;
        }

        @Override
        public FakeEntity2 clone() {
            try {
                return (FakeEntity2) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    @Test
    public void createAndGet_One() {
        // Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();
        dataBase.create(new FakeEntity1());

        // Act
        List<FakeEntity1> allOfType = dataBase.getAllOfType(FakeEntity1.class);
        int id = allOfType.get(0).id;

        // Assert
        assertEquals(1, allOfType.size());
        assertEquals(id, dataBase.getByID(id,FakeEntity1.class).getId());
        assertEquals(FakeEntity1.class, dataBase.getByID(id,FakeEntity1.class).getClass());
    }

    @Test
    public void createAndGet_Two() {
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();
        dataBase.create(new FakeEntity1());
        dataBase.create(new FakeEntity1());

        //Act
        List<FakeEntity1> allOfType = dataBase.getAllOfType(FakeEntity1.class);
        int id1 = allOfType.get(0).id;
        int id2 = allOfType.get(1).id;

        //Assert
        assertEquals(2, allOfType.size());
        assertEquals(id1, dataBase.getByID(id1,FakeEntity1.class).getId());
        assertEquals(FakeEntity1.class, dataBase.getByID(id1,FakeEntity1.class).getClass());
        assertEquals(id2, dataBase.getByID(id2,FakeEntity1.class).getId());
        assertEquals(FakeEntity1.class, dataBase.getByID(id2,FakeEntity1.class).getClass());
    }

    @Test
    public void createAndGet_TwoOfDifferentType(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();
        dataBase.create(new FakeEntity1());
        dataBase.create(new FakeEntity2());

        //Act
        List<FakeEntity1> allOfType = dataBase.getAllOfType(FakeEntity1.class);
        int id1 = allOfType.get(0).id;

        //Assert
        assertEquals(1, allOfType.size());
        assertEquals(id1, dataBase.getByID(id1,FakeEntity1.class).getId());
        assertEquals(FakeEntity1.class, dataBase.getByID(id1,FakeEntity1.class).getClass());
    }

    @Test
    public void createAndGet_OneWithPresetId() {
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();
        FakeEntity1 entity = new FakeEntity1();
        entity.setId(5);
        dataBase.create(entity);

        //Act
        Entity retrievedEntity = dataBase.getByID(5,FakeEntity1.class);

        //Assert
        assertEquals(5, retrievedEntity.getId());
        assertEquals(FakeEntity1.class, retrievedEntity.getClass());
    }

    @Test
    public void createAndGet_TwoOfDifferentTypeWithSamePresetId() {
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        FakeEntity1 entity1 = new FakeEntity1();
        entity1.setId(5);
        dataBase.create(entity1);

        FakeEntity2 entity2 = new FakeEntity2();
        entity2.setId(5);
        dataBase.create(entity2);

        //Act
        Entity retrievedEntity = dataBase.getByID(5,FakeEntity1.class);

        //Assert
        assertEquals(5, retrievedEntity.getId());
        assertEquals(FakeEntity1.class, retrievedEntity.getClass());
    }

    @Test(expected = IllegalArgumentException.class)
    public void create_TwoOfSameTypeAndSamePresetId_creationFails(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        FakeEntity1 entity1 = new FakeEntity1();
        entity1.setId(5);
        dataBase.create(entity1);

        FakeEntity1 entity2 = new FakeEntity1();
        entity2.setId(5);

        //Act
        dataBase.create(entity2);
    }

    @Test
    public void getId_notExistingEntity(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        //Act
        FakeEntity1 entity1 = dataBase.getByID(5,FakeEntity1.class);

        //Assert
        assertNull(entity1);
    }

    @Test
    public void entityCloning_changeOriginal(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        FakeEntity1 entity1 = new FakeEntity1();
        entity1.data = 10;
        dataBase.create(entity1);

        //Act
        entity1.data = 20;

        //Assert
        assertEquals(10, dataBase.getByID(entity1.id,FakeEntity1.class).data);
    }

    @Test
    public void entityCloning_changeRetrieved(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        FakeEntity1 entity1 = new FakeEntity1();
        entity1.data = 10;
        dataBase.create(entity1);

        //Act
        dataBase.getByID(entity1.id,FakeEntity1.class).data = 20;

        //Assert
        assertEquals(10, dataBase.getByID(entity1.id,FakeEntity1.class).data);
    }

    @Test
    public void entityCloning_changeRetrievedAllOfType(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        FakeEntity1 entity1 = new FakeEntity1();
        entity1.data = 10;
        dataBase.create(entity1);

        //Act
        dataBase.getAllOfType(FakeEntity1.class).forEach(fakeEntity1 -> fakeEntity1.data = 20);

        //Assert
        dataBase.getAllOfType(FakeEntity1.class).forEach(fakeEntity1 -> assertEquals(10, fakeEntity1.data));
    }

    @Test
    public void entityUpdate(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        FakeEntity1 entity1 = new FakeEntity1();
        entity1.data = 10;
        dataBase.create(entity1);

        FakeEntity1 entity2 = new FakeEntity1();
        entity2.setId(entity1.id);
        entity2.data=20;

        //Act
        dataBase.update(entity2);

        //Assert
        assertEquals(20, dataBase.getByID(entity1.id,FakeEntity1.class).data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void entityUpdate_differentTypes_updateFails(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        FakeEntity1 entity1 = new FakeEntity1();
        entity1.data = 10;
        dataBase.create(entity1);

        FakeEntity2 entity2 = new FakeEntity2();
        entity2.setId(entity1.id);
        entity2.data=20;

        //Act
        dataBase.update(entity2);

        //Assert
        assertEquals(10, dataBase.getByID(entity1.id,FakeEntity1.class).data);
    }

    @Test(expected = IllegalArgumentException.class)
    public void entityDelete_nonExisting_exception(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        //Act
        dataBase.delete(0,FakeEntity1.class);
    }

    @Test
    public void entityDelete_existing_deleteWorks(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        FakeEntity1 entity1 = new FakeEntity1();
        dataBase.create(entity1);

        FakeEntity1 entity2 = new FakeEntity1();
        dataBase.create(entity2);

        //Act
        dataBase.delete(entity1.id,FakeEntity1.class);

        //Assert
        assertEquals(1, dataBase.getAllOfType(FakeEntity1.class).size());
        assertEquals(entity2.id, dataBase.getAllOfType(FakeEntity1.class).get(0).id);
    }

    @Test
    public void entityDelete_TwoEntitiesOfDifferentTypeAndSameId_TheRightOneIsDeleted(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();

        FakeEntity1 entity1 = new FakeEntity1();
        entity1.setId(5);
        dataBase.create(entity1);

        FakeEntity2 entity2 = new FakeEntity2();
        entity2.setId(5);
        dataBase.create(entity2);

        //Act
        dataBase.delete(5,FakeEntity1.class);

        //Assert
        List<FakeEntity1> allOfType1 = dataBase.getAllOfType(FakeEntity1.class);
        List<FakeEntity2> allOfType2 = dataBase.getAllOfType(FakeEntity2.class);
        assertEquals(0, allOfType1.size());
        assertEquals(1, allOfType2.size());
    }

    @Test
    public void getAllOfType_noEntitiesOfType_returnsEmptyList(){
        //Arrange
        CrudDataBase dataBase = new InMemoryMapDataBase();
        dataBase.create(new FakeEntity1());

        //Act
        List<FakeEntity2> allOfType = dataBase.getAllOfType(FakeEntity2.class);

        //Assert
        assertEquals(0, allOfType.size());
    }
}