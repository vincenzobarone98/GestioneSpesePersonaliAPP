Sistema di Gestione Spese Personali con Java e MySQL
Questo progetto consiste in un'applicazione Java che si connette a un database MySQL per gestire i dati delle proprie spese personali. L'applicazione offre funzionalità CRUD (Create, Read, Update, Delete) per le spese e le categorie.

Requisiti
Java Development Kit (JDK) versione 8 o successiva
MySQL Server
Librerie JDBC per MySQL (solitamente fornite dal driver MySQL)

Importare il progetto in un IDE Java come IntelliJ IDEA o Eclipse.

Assicurarsi che il server MySQL sia in esecuzione sulla propria macchina locale o sia accessibile.

Configurare le credenziali di accesso al database nel file DatabaseConnection.java.

Eseguire il file DatabaseSetup.java per creare il database e le tabelle necessarie.

Eseguire l'applicazione eseguendo la classe Main.java.

Utilizzo
Una volta avviata l'applicazione, sarà possibile:

Aggiungere, visualizzare, modificare ed eliminare spese e categorie utilizzando l'interfaccia grafica.
Salvare in formato pdf le spese.
Cercare le spese secondo nome della categoria o data.
Inserire manualmente i dati richiesti.
Le operazioni di modifica ed eliminazione richiederanno l'inserimento dell'ID del record corrispondente.

