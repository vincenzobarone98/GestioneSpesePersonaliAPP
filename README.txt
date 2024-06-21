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
Le prime 5 categorie (Alimentari, Trasporti, Bollette, Svago, Abbigliamento) sono preimpostate tramite una query e non sono modificabili ne eliminabili.
Le spese sono ordinate per data.
Per eliminare una categoria, bisogna prima eliminate tutte le spese ad essa associate.
Esportare i dati relativi alle spese in formato pdf.


Struttura del Progetto
Il progetto è strutturato come segue:

src/gestioneSpese: Contiene il codice sorgente Java.
Main.java: Classe principale dell'applicazione che gestisce l'interfaccia grafica e le interazioni con il database.
Categoria.java: Classe che rappresenta una categoria.
Spesa.java: Classe che rappresenta una spesa.
CategoriaDAO.java: Classe per l'accesso ai dati delle categorie nel database.
SpesaDAO.java: Classe per l'accesso ai dati delle spese nel database.
DatabaseConnection.java: Classe per la gestione della connessione al database.
DatabaseSetup.java: Classe per la creazione delle tabelle e l'inserimento di dati delle categorie standard nel database.
Pdf.java: Classe per l' estrazione dei dati relativi alle spese e Salvataggio in formato PDF.

