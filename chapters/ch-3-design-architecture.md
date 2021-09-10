# Design architetturale

## Architettura complessiva

### Descrizione di pattern architetturali utilizzati

#### Pattern Model View Controller (MVC)

Model-view-controller è un pattern architetturale in grado di separare la logica di presentazione dei dati (di cui si occupa la view) dalla logica di business (di cui si occupano model e controller), utilizzando una architettura multi-tier (basata quindi sui tre livelli da cui prende il nome).

- Model: si occupa di catturare i comportamenti di sistema in termini di dominio del problema, al suo interno pertanto sono definiti dati e logica dell'applicativo.
- View: si occupa di rappresentare le informazioni trattate che interessano l'utente in diversi modi, nel caso di
  ScalaQuest diverse GUI consentono la navigazione dei nodi durante l'attività di gioco e la visione della struttura vera e propria delle storie all'interno dell'editor.
- Controller (nel nostro caso molteplici):  ha il compito di accettare l'input che generalmente deriva dalla view e convertirlo in comandi per il model o per la view stessa. Segue una descrizione dettagliata del funzionamento e delle interazioni dei vari controlli presenti nell'applicativo.

#### Flusso d'esecuzione dei controller

Durante l'esecuzione del programma, il primo controller a prendere la parola è l'ApplicationControllerSingleton; la scelta di utilizzare un singleton deriva dal fatto che esisterà sempre una sola e unica istanza di tale controller in quanto esso si trova sulla cima della gerarchia di tutti i controller.

TODO: inserire un diagramma che faccia vedere la gerarchia di controller

Partendo dunque dall'ApplicationController, il flusso d'esecuzione può intraprendere differenti direzioni dipendentemente dalle scelte compiute dall'utente:

- Selezione di una storia  
  L'utente seleziona, attraverso il menu principale, una storia che intende giocare. Dipendentemente dalla presenza o meno di un salvataggio collegato alla storia selezionata, il flusso di controllo si divide in due diramazioni:
    - Nuova partita: questa scelta sarà sempre disponibile, indipendentemente dalla presenza di un salvataggio.  
      Il controllo viene inizialmente passato al PlayerConfigurationController che, insieme alla View collegata,
      permette all'utente di creare il proprio personaggio.  
      Tramite l'input fornito dall'utente viene creato un Player che, insieme alla struttura di StoryNode (recuperata dall'ApplicationControllerSingleton), viene utilizzato per creare uno StoryModel, passando il controllo al GameMasterController.
    <div align="center">
        <img src="https://images2.imgbox.com/da/c3/RgRPV8vL_o.png" alt="Diagramma di sequenza - nuova partita">
        <p align="center">Diagramma di sequenza - nuova partita</p>
    </div>

    - Continua partita : differentemente dalla precedente, questa opzione sarà disponibile solo in presenza di un
      salvataggio.  
      In questo caso l'utente non avrà necessità di creare un nuovo personaggio in quanto esso sarà già stato creato in una sessione di gioco precedente.  
      L'ApplicationControllerSingleton dunque reperirà la storia e il salvataggio annesso ricreando lo StoryModel e
      riportando il giocatore nella stessa situazione in cui egli aveva salvato il proprio progresso; lo StoryModel
      ricostruito verrà dunque utilizzato dal GameMasterController per continuare il gioco.
      <div align="center">
          <img src="https://images2.imgbox.com/73/3f/X6GPoddK_o.png" alt="Diagramma di sequenza - continua partita">
          <p align="center">Diagramma di sequenza - continua partita</p>
      </div>

  Entrambi i flussi di esecuzione condurranno quindi al GameMasterController, così da poter permettere all'utente di
  giocare.  
  Il GameMasterController dispone di molteplici SubController; ognuno dei quali utilizza il GameMasterController come "ponte" richiamando il metodo executeOperation e specificando quale altro SubController desidera passare il
  controllo.  
  Quando il GameMasterController riceverà una richiesta di esecuzione di un'operazione, reperirà il SubController specificato e garantirà ad esso il controllo.
    <div align="center">
        <img src="https://images2.imgbox.com/17/b7/4LOzH674_o.png" alt="Diagramma di sequenza - esecuzione di una partita">
        <p align="center">Diagramma di sequenza - esecuzione di una partita</p>
    </div>
    In questo esempio per semplicità sono stati inclusi solo lo StoryController (responsabile della navigazione all'interno dei nodi) e l'InventoryController, ma tale meccanismo è espandibile con un numero di controller a piacere rendendo estremamente facile introdurne di nuovi.  

- Utilizzo dell'editor  
  TODO

### Scelte tecnologiche cruciali ai fini architetturali

<!-- NOTA: opzionalmente -->

---