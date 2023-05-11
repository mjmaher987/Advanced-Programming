import java.lang.reflect.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//some of these are taken from net

public class Agent {
    public List<String> getMethodNames(Object object) {
        List<String> allMethods = new ArrayList<>();
        Method[] methods = object.getClass().getDeclaredMethods();
        for (Method method : methods) {
            String methodName = method.getName();
            allMethods.add(methodName);
        }
        return allMethods;
    }

    public Object getFieldContent(Object object, String fieldName) throws Exception {
        Field field = null;
        try {
            field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException();
        }

        return field.get(object);
    }

    public void setFieldContent(Object object, String fieldName, Object content) throws Exception {
        Field field = null;
        try {
            field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new NoSuchFieldException();
        }
        if (field == null) {
            throw new NoSuchFieldException();
        }
        Field modifiersField = Field.class.getDeclaredField("modifiers");
        modifiersField.setAccessible(true);
        modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
        try {
            field.set(object, content);
        } catch (Exception e) {

        }

    }

    public Object call(Object object, String methodName, Object[] parameters) throws Exception {
        Class[] classes = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            classes[i] = parameters[i].getClass();
            System.out.println(classes[i]);
        }

        Method method = null;
        try {
            method = object.getClass().getDeclaredMethod(methodName, Arrays.stream(parameters).map(Object::getClass).toArray(Class[]::new));
            method.setAccessible(true);
            return method.invoke(object, parameters);
        } catch (NoSuchMethodException e) {
            throw new NoSuchMethodException();
        }

    }

    public Object createANewObject(String fullClassName, Object[] initials) throws Exception {
        Class<?> classForCreateNewObject = null;
        try {
            classForCreateNewObject = Class.forName(fullClassName);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException();
        }

        return classForCreateNewObject.getConstructor(Arrays.stream(initials).map(Object::getClass).toArray(Class[]::new)).newInstance(initials);

    }

    public String debrief(Object object) {
        ArrayList<String> stringBuildersArray = new ArrayList<>();
        Field[] allFields = object.getClass().getDeclaredFields();
        HashMap<String, Field> hashmapField = new HashMap<>();
        ArrayList<String> allFieldNames = new ArrayList<>();
        for (int i = 0; i < allFields.length; i++) {
            allFieldNames.add(allFields[i].getName());
            hashmapField.put(allFieldNames.get(i), allFields[i]);
        }
        Collections.sort(allFieldNames);
        for (int i = 0; i < allFieldNames.size(); i++) stringBuildersArray.add(stringForFieldInDebrief(hashmapField.get(allFieldNames.get(i)).toString()));
        String fieldString = "";
        for (String s : stringBuildersArray) fieldString += (s + "\n");

        HashMap<String, Method> methodHashMap = new HashMap<>();
        ArrayList<String> stringBuildersArrayMethod = new ArrayList<>();
        Method[] allMethods = object.getClass().getDeclaredMethods();
        ArrayList<String> allMethodNames = new ArrayList<>();
        String methodString = giveMeMethodString(methodHashMap, stringBuildersArrayMethod, allMethods, allMethodNames);
        return "Name: " + object.getClass().getSimpleName() + "\nPackage: " + object.getClass().getPackage().getName()
                + "\nNo. of Constructors: " + object.getClass().getDeclaredConstructors().length +
                "\n===\nFields:\n" + fieldString + "(" + allFields.length + " fields)\n===\nMethods:\n" + methodString
                + "(" + allMethods.length + " methods)";
    }

    private String giveMeMethodString(HashMap<String, Method> methodHashMap, ArrayList<String> stringBuildersArrayMethod, Method[] allMethods, ArrayList<String> allMethodNames) {
        for (int i = 0; i < allMethods.length; i++) {
            allMethodNames.add(allMethods[i].getName());
            methodHashMap.put(allMethodNames.get(i), allMethods[i]);
        }
        Collections.sort(allMethodNames);

        for (int i = 0; i < allMethodNames.size(); i++) {
            String allInputs = "";
            int counter = 0;
            for (Class<?> parameterType : methodHashMap.get(allMethodNames.get(i)).getParameterTypes()) {
                allInputs += parameterType.getSimpleName();
                if (counter != methodHashMap.get(allMethodNames.get(i)).getParameterTypes().length - 1) {
                    allInputs += ", ";
                }
                counter++;
            }
            stringBuildersArrayMethod.add(methodHashMap.get(allMethodNames.get(i)).getReturnType().getSimpleName() + " " + allMethodNames.get(i) + "(" + allInputs + ")");
        }

        String methodString = "";
        for (String s : stringBuildersArrayMethod) {
            methodString += (s + "\n");
        }
        return methodString;
    }

    //This comment won't be deleted becauseit may be needed for Tahvil
//    private String stringForMethodInDebrief(String string) {
//        Pattern pattern = Pattern.compile("\\((\\S+)(\\.)(\\S+)");
//        Matcher matcher = pattern.matcher(string);
//        String answer = string;
//        boolean didItFindAnyThing = false;
//
//        System.out.println(answer);
//        while (matcher.find()) {
//            didItFindAnyThing = true;
//            answer = answer.replace(matcher.group(1) + matcher.group(2), "");
//            matcher = pattern.matcher(answer);
//        }
//        System.out.println(answer);
//
//
//        Pattern pattern2 = Pattern.compile("(\\S+)(\\.)");
//        Matcher matcher2 = pattern2.matcher(answer);
//        while (matcher2.find()) {
//            didItFindAnyThing = true;
//            answer = answer.replace(matcher2.group(1) + matcher2.group(2), "");
//            matcher2 = pattern2.matcher(answer);
//        }
//        System.out.println(answer);
//
//
//        Pattern pattern1 = Pattern.compile("\\((\\S+)(\\$)");
//        Matcher matcher1 = pattern1.matcher(answer);
//        while (matcher1.find()) {
//            didItFindAnyThing = true;
//            answer = answer.replace(matcher1.group(1) + matcher1.group(2), "");
//            matcher1 = pattern1.matcher(answer);
//        }
//        System.out.println(answer);
//
//        Pattern pattern3 = Pattern.compile("(\\S+)(\\$)");
//        Matcher matcher3 = pattern3.matcher(answer);
//        while (matcher3.find()) {
//            didItFindAnyThing = true;
//            answer = answer.replace(matcher3.group(1) + matcher3.group(2), "");
//            matcher3 = pattern3.matcher(answer);
//        }
//        System.out.println(answer);
//
//        if (didItFindAnyThing) {
//            answer = answer.replace("public ", "");
//            answer = answer.replace("private ", "");
//            answer = answer.replace("static ", "");
//            answer = answer.replace("protected ", "");
//            return answer;
//        }
//        string = string.replace("public ", "");
//        string = string.replace("private ", "");
//        string = string.replace("static ", "");
//        string = string.replace("protected ", "");
//        return string;
//    }

    private String stringForFieldInDebrief(String allField) {
        Pattern pattern = Pattern.compile("(\\S+)(\\.|\\$)(\\S+)");
        Matcher matcher = pattern.matcher(allField);
        String answer = "";
        answer = allField;
        boolean didItFindAnyThing = false;
        while (matcher.find()) {
            didItFindAnyThing = true;
            answer = answer.replace(matcher.group(1) + matcher.group(2), "");

        }
        if (didItFindAnyThing) {
            return answer;
        }
        return allField;
    }


    public Object clone(Object toClone) throws Exception {
        Constructor constructor = toClone.getClass().getDeclaredConstructor();
        constructor.setAccessible(true);
        return doNeededActoionsOfClone(toClone, constructor);
    }

    private Object doNeededActoionsOfClone(Object toClone, Constructor constructor) {
        Object newObject = null;
        try {
            newObject = constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        for (Field declaredField : toClone.getClass().getDeclaredFields()) {
            try {
                doNeededActoionsOfFields(newObject, toClone, declaredField);
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
        Class superOfHelp = toClone.getClass().getSuperclass();
        Class helpClass = toClone.getClass();
        while (helpClass.getSuperclass() != null) {
            for (Field declaredField : superOfHelp.getDeclaredFields()) {
                try {
                    doNeededActoionsOfFields(newObject, toClone, declaredField);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            helpClass = helpClass.getSuperclass();
        }
        return toClone;
    }

    private void doNeededActoionsOfFields(Object toClone, Object newObject, Field declaredField) throws Exception {
        fieldFinction(declaredField);
        if (firstBooleanOfField(declaredField)) {
            arrayFunctionInClone(declaredField, toClone, newObject);
        }
        else if (secondBooleanOfField(declaredField, toClone)) {
            declaredField.set(newObject, declaredField.get(toClone));
        }
        else {
            try {
                declaredField.set(newObject, clone(declaredField.get(toClone)));
            } catch (Exception e) {

            }

        }
    }

    private void arrayFunctionInClone(Field declaredField, Object toClone, Object newObject) throws Exception {
        Object creatingArray = Array.newInstance(declaredField.getType().getComponentType(), Array.getLength(declaredField.get(toClone)));
        for (int i = 0; i < Array.getLength(declaredField.get(toClone)); i++) {
            Object object = Array.get(declaredField.get(toClone), i);
            try {
                Array.set(creatingArray, i, clone(object));
            } catch (Exception e) {

            }

        }
        declaredField.set(newObject, creatingArray);
    }

    private boolean secondBooleanOfField(Field declaredField, Object toClone) throws IllegalAccessException {
        Class fieldClass = declaredField.getType();
        return fieldClass.equals(Boolean.class) || fieldClass.equals(String.class) || fieldClass.isPrimitive() || declaredField.get(toClone) instanceof Enum;
    }

    private boolean firstBooleanOfField(Field declaredField) {
        return declaredField.getType().isArray();
    }

    private void fieldFinction(Field declaredField) {
        declaredField.setAccessible(true);
        Field modifier = null;
        try {
            modifier = Field.class.getDeclaredField("modifiers");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        modifier.setAccessible(true);
        try {
            modifier.setInt(declaredField, declaredField.getModifiers());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

//    private Constructor constructorFunction(Object toClone) {
//        try {
//            Constructor constructor = toClone.getClass().getDeclaredConstructor();
//            constructor.setAccessible(true);
//            return constructor;
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }
//        return null;
//
//    }

//    private <T> T copy(T entity) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
//        Class<?> clazz = entity.getClass();
//        Constructor constructor = clazz.getDeclaredConstructor();
//        constructor.setAccessible(true);
//        T newEntity = (T) constructor.newInstance();
//
//        while (clazz != null) {
//
//            copyFields(entity, newEntity, clazz);
//            clazz = clazz.getSuperclass();
//        }
//
//        return newEntity;
//    }
//
//    private <T> T copyFields(T entity, T newEntity, Class<?> clazz) throws IllegalAccessException {
//        List<Field> fields = new ArrayList<>();
//        for (Field field : clazz.getDeclaredFields()) {
//            fields.add(field);
//        }
//        for (Field field : fields) {
//            field.setAccessible(true);
//            field.set(newEntity, field.get(entity));
//        }
//        return newEntity;
//    }



}
