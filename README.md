<b>Bugs:</b>

  1) El metodo handlerNumbers debe de permitir solo
  el ingreso de numero. Pero no acepta ninguno,
  el metodo handlerLetters cumple con la misma
  funcion, pero con la diferencia que solo acepta
  letras.

  <u>Nota:</u> todo parece indicar que la expresion
  regular que usa para establecer esta condicion esta
  generando fallos solo con el metodo handlerNumber.

  <u>Ubicacion:</u> src/main/java/com/example/demo/control/HelloController
  >linea: 172

  2) El archivo demo.jar y demo.exe no se ejecutan

  <u>Ubicacion:</u> out/artifacts/demo_jar


<b>Compilacion:</b>
 
Ya javaFX y jfoenix lanzan la excepción de java reflect
se debe configurar el run con la siguientes directivas 
en la configuracion de VM

--add-opens java.base/java.lang.reflect=ALL-UNNAMED
--add-opens=java.base/java.lang.reflect=com.jfoenix

