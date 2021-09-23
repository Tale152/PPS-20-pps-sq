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

All'interno del sistema sono presenti più controller; ognuno di essi è associato ad una differente View mentre, per quanto riguarda il Model, le classi da manipolare sono le stesse in comune tra tutti i controller. L'effettivo controllo viene acquisito da un diverso controller dipendentemente dai servizi necessari in un dato momento.  
Durante l'esecuzione del programma, il primo controller a prendere la parola è l'ApplicationControllerSingleton; la scelta di utilizzare un singleton deriva dal fatto che esisterà sempre una sola e unica istanza di tale controller in quanto esso si trova sulla cima della gerarchia di tutti i controller.

<div align="center">
    <img src="https://images2.imgbox.com/ca/14/qUVMfwc3_o.png" alt="Gerarchia dei controller">
    <p align="center">Gerarchia dei controller</p>
</div>

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
  Nel momento in cui l'utente selezioni l'editor, verrà aperta una dialog che chiederà se si voglia continuare a modificare una storia (caricandola da file) o se invece se ne voglia creare una nuova. Quale che sia la scelta dell'utente verrà creata una gerarchia di StoryNode (creandone una nuova o ricostruendola da file) sulla quale l'utente potrà eseguire operazioni attraverso la View.  

  Vi sono in questo caso un solo Controller ed una sola View; per l'effettiva manipolazione dei dati, una volta che l'utente avrà selezionato l'operazione che intende compiere, la View creerà dei form attraverso il componente FormBuilder da noi implementato al fine di permettere all'utente di inserire dell'input.  

  FormBuilder dunque si occuperà di creare dei form dinamici semplicemente specificando i componenti attraverso i quali fornire l'input e un listener che definisca cosa fare nel momento in cui i dati inseriti nel form vengono confermati (in questo caso richiamare i metodi dell'EditorController, ma è possibile riutilizzare FormBuilder in qualsiasi contesto).

  ``` scala
  val form: Form = FormBuilder()
        .addIntegerField("Which story node is the starting node? (id)")
        .addTextField("What description should the pathway to the new story node show?")
        .addTextAreaField("What narrative should the new story node show?")
        .get(editorController)
  form.setOkButtonListener(NewStoryNodeOkListener(form, editorController))
  form.render()
  ```

## Stati del sistema
<div align="center">
<img src="https://images2.imgbox.com/95/ee/82VOQeYA_o.png" alt="Diagramma di sequenza - esecuzione di una partita">
<p align="center">Diagramma degli stati - menu principale</p>
</div>
All'avvio dell'applicativo il sistema mostrerà il menu principale. Da qui vi è la possibilità di:

- Giocare ad una delle storie disponibili caricando (se possibile) progressi precedentemente svolti all'interno della stessa o iniziando una nuova partita;
- Utilizzare l'editor di storie riprendendo il lavoro da dove lo si era lasciato (ricostruendo il model attraverso un file precedentemente salvato) o cominciando un nuovo editing da zero;
- Aggiungere nuove storie utilizzando i file generati dall'editor;
- Rimuovere storie precedentemente aggiunte.

### Playing
<div align="center">
<img src="https://images2.imgbox.com/c7/07/IQBeAYQ8_o.png" alt="Diagramma di sequenza - in gioco">
<p align="center">Diagramma degli stati - in gioco</p>
</div>
Avviata l'esplorazione di una storia, innanzitutto il sistema permetterà all'utente di creare un personaggio da impersonare all'interno dell'avventura (solo se la partita è iniziata senza caricare progressi predenti).  

Caricato il nodo corrente, viene innanzitutto controllato se questo contiene un nemico; in caso affermativo verrà avviata una battaglia.  

Nel caso in cui non vi sia stata una battaglia o il giocatore sia uscito vittorioso da quest'ultima, verrà controllato se il nodo corrente contiene degli eventi. Nel caso vi siano degli eventi da svolgere essi verranno eseguiti in sequenza.  

Una volta gestiti gli eventuali eventi verranno presentate all'utente le varie strade che egli potrà intraprendere partendo da questo nodo (oltre che la possibilità di accedere all'inventario, salvare i progressi, visualizzare le statistiche, ecc...); saranno visualizzate solo le strade che soddisfino eventuali condizioni imposte sulle stesse.  

Scelta la strada attraverso la quale continuare la storia, viene caricato un nuovo StoryNode, ripetendo quanto descritto precedentemente.  

Tale ciclo continuerà finchè non si verificherà una di queste tre condizioni:
- Viene raggiunto un nodo finale (cioè che non contiene alcuna strada percorribile);
- Il giocatore viene sconfitto in una battaglia;
- L'utente sceglie di uscire dalla partita.

Vediamo ora nel dettaglio lo stato "in a battle" (di complessità maggiore rispetto agli altri stati composti).

### In a battle
<div align="center">
<img src="https://images2.imgbox.com/1e/4f/EEJM41pe_o.png" alt="Diagramma di sequenza - in battaglia">
<p align="center">Diagramma degli stati - in battaglia</p>
</div>

All'interno di una battaglia l'utente è posto davanti a quattro possibili scelte:
- Uscire dal gioco
- Utilizzare un oggetto 
L'utente, attraverso l'inventario (opportunamente filtrato per mostrare solo oggetti utilizzabili in battaglia), utilizza un oggetto. Successivamente a tale azione, il nemico attacca il giocatore. 
- Tentare la fuga
Il giocatore sceglie la fuga che, però, non è garantita. Attraverso un algoritmo (che utilizza le caratteristiche dei due personaggi sfidanti), viene determinato se il tentativo di fuga ha avuto successo (nel caso di insuccesso il giocatore verrà colpito dal nemico).
- Attaccare il nemico
In questo caso, un algoritmo (di natura simile al precedente), determina l'ordine in cui avverranno gli attacchi dei due personaggi coinvolti in battaglia. Verranno dunque gestite l'eventuale morte del giocatore (game over) o del nemico (vittoria).

### Using editor
<div align="center">
<img src="https://images2.imgbox.com/dc/ed/YvFHrJgV_o.png" alt="Diagramma di sequenza - editor">
<p align="center">Diagramma degli stati - editor</p>
</div>
L'editor presenta lo stesso comportamento per tutte le operazioni possibili (con diversi effetti sul model in base all'operazione selezionata).  

Viene dinamicamente generato un form finalizzato all'inserimento dei dati necessari al compimento dell'operazione; nel momento in cui l'utente desideri confermare l'inserimento dei dati questi ultimi vengono valutati e, in caso positivo, il model viene aggiornato (così come la rappresentazione grafica di tale model). 

### Scelte tecnologiche cruciali ai fini architetturali
#### Tecnologie scartate
In questa sezione verranno illustrate alcune tecnologie che in fase di analisi sono state prese in considerazione e successivamente abbandonate. Seguono descrizioni e motivazioni.
##### Modello ad attori (Akka)
Durante la progettazione della parte dei Controller sono state riscontrate alcune similarità tra il modello proposto (quello finale, già illustrato precedentemente) e il [modello ad attori](https://doc.akka.io/docs/akka/current/typed/actors.html#:~:text=com%2Fakka%2Fakka-,Akka%20Actors,correct%20concurrent%20and%20parallel%20systems).
Per quanto riguarda la fase di gioco infatti, nel modello proposto un Controller principale (_GameMasterController_) si occupa di creare i sub controller e funge da loro coordinatore.
I controller erano quindi inizialmente stati pensati in modo differente. Modificando sensibilmente la struttura del pattern MVC si puntava a far diventare i Controller degli Attori.

Nella struttura presentata _GameMasterController_ diventa un attore e tutti i sub controller diventano attori figli.
Alcuni benefici di questa modellazione:
- Una volta istanziati gli attori è possibile per ognuno di loro dialogare con altri attori del sistema conoscendo il loro identificativo in modo trasparente senza passare tramite l'attore padre.
- Un'eventuale estensione del gioco nel distribuito sarebbe probabilmente più facilmente sviluppabile.
- È possibile avere molta più varietà sui contenuti che i controller possono mandarsi tra loro; molto utile nel caso in cui i controller abbiano bisogno di coordinarsi tra loro e prendere decisioni in funzione dello stato degli altri.
> **_Un esempio_** :  
durante una battaglia è possibile utilizzare oggetti all'interno dell'inventario. Esistono quindi due controller, _InventoryController_ e _BattleController_ che dialogano tra loro. Grazie al modello ad attori _BattleController_ potrebbe capire se è stato utilizzato uno strumento o no in base al tipo o al contenuto di un messaggio inviat da _InventoryController_.

Nelle fasi iniziali del progetto sono stati realizzati alcuni prototipi dell'applicazione aventi questa struttura e sono stati riscontrate le seguenti criticità:

- Trasformare i Controller in attori significherebbe trasformare anche le View in attori, facendo venire ancora meno la struttura del pattern MVC.
- Il comportamento dei Controller dipenderebbe fortemente dal tipo di messaggi che può ricevere. Questo tipo di architettura è particolarmente scalabile, ma mano a mano che il sistema cresce tende anche ad essere particolarmente dispersiva. Il numero di tipi di messaggi all'interno del sistema sarebbe molto elevato e la navigazione del codice di conseguenza più tediosa.
- La natura dell'applicazione non richiede mai che più di un attore esegua il suo comportamento in contemporanea ad altri.
- Il modello a scambio di messaggi pecca in performance: inviare messaggi è generalmente un'operazione più lenta rispetto alla chiamata di metodo.

A seguito di questa analisi è stato unimamente deciso di non utilizzare il framework Akka e di seguire il pattern MVC.
##### Serializzazione e deserializzazione tramite librerie json
<!-- NOTA: opzionalmente -->

---