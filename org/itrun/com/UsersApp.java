package org.itrun.com;

public class UsersApp {

    public static void main(String[] args) {

        EmployeeDatabase database = new EmployeeDatabase();

        Person internalPerson = new Person("1", "Kacper", "Novak", "123456789", "kacper@mail.com", "12345678901");
        Person internalPerson2 = new Person("2", "Anna", "Pieczarka", "111222333", "anna@mail.com", "12345676543");
        Person internalPerson3 = new Person("3", "Marcin", "Horwacik", "123456789", "marcin@mail.com", "12345543211");
        Person externalPerson = new Person("1", "Janek", "Migielski", "987654321", "jan@mail.com", "98765433210");
        Person externalPerson2 = new Person("2", "Jan", "Nowakowski", "11111111", "jan2222@mail.com", "9935432108");
        Person externalPerson3 = new Person("3", "Marta", "Nowak", "987654321", "marta23@mail.com", "98000032107");

        database.create(internalPerson, EmployeeDatabase.Type.INTERNAL);
        database.create(internalPerson2, EmployeeDatabase.Type.INTERNAL);
        database.create(internalPerson3, EmployeeDatabase.Type.INTERNAL);
        database.create(externalPerson, EmployeeDatabase.Type.EXTERNAL);
        database.create(externalPerson2, EmployeeDatabase.Type.EXTERNAL);
        database.create(externalPerson3, EmployeeDatabase.Type.EXTERNAL);

        System.out.println(database.find(EmployeeDatabase.Type.INTERNAL, "Kacper", null, null, null));
        System.out.println(database.find(EmployeeDatabase.Type.EXTERNAL, "Jan", null, null, null));
        System.out.println(database.find(EmployeeDatabase.Type.EXTERNAL, "Marta", null, null, null));

        System.out.println(database.remove("3", EmployeeDatabase.Type.INTERNAL));
        System.out.println(database.remove("2", EmployeeDatabase.Type.EXTERNAL));

        internalPerson.setFirstName("Updated Kacper");
        database.modify(internalPerson, EmployeeDatabase.Type.INTERNAL);
        System.out.println(internalPerson.getFirstName());
        System.out.println(database.find(EmployeeDatabase.Type.INTERNAL, "Updated Kacper", null, null, null));
    }
}