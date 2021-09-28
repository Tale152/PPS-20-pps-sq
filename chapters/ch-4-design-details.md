# 4 - Design di dettaglio

## Scelte rilevanti

## Stati del sistema
<div align="center">
<img src="https://images2.imgbox.com/73/62/WhxIIroY_o.png" alt="Diagramma di sequenza - esecuzione di una partita">
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
<img src="https://images2.imgbox.com/97/ce/RABb77Vp_o.png" alt="Diagramma di sequenza - in battaglia">
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

## Organizzazione del codice

<!-- NOTA: corredato da pochi ma efficaci diagrammi -->
<!-- Lista dei vari componenti del sistema -->

## Pattern di progettazione
<!-- Lista dei vari pattern utilizzati, perché e dove -->
### Model View Controller (MVC)

### Factory Pattern
Factory è un pattern creazionale fondamentale utile per la creazione di oggetti.  
Nel linguaggio Scala il metodo factory convenzionale è denominato ```apply()```.
Il metodo viene utilizzato all'interno di oggetti denominati _companion object_, cioè oggetti che vengono affiancati ai trait e che proprio grazie al metodo ```apply()``` ritornano determinate instanze di classi sulla base degli argomenti passati.  

In ScalaQuest vengono utilizzati abbondantemente i metodi factory per la creazione di tutti gli oggetti dell'applicazione.

### Strategy Pattern
Il pattern strategy viene utilizzato per isolare un algoritmo (la strategia, la strategy) all'interno di un oggetto.  
Strategy prevede che gli algoritmi siano intercambiabili tra loro, in base ad una specificata condizione, in modalità trasparente al client che ne fa uso.  

ScalaQuest fa uso di pattern Strategy in diversi punti dell'applicazione, per esempio all'interno della classe ```StatModifier```.  
```StatModifier``` è una classe di Model che si occupa di incapsulare un algoritmo che modifica in un certo modo una determinata statistica.  
Un oggetto ```StatModifier``` quindi contiene una funzione che modifica un valore intero di input (la statistica) in un valore intero di output (la statistica con valore aggiornato).  
Nel linguaggio Scala una funzione di questo tipo è facilmente esprimibile grazie al paradigma funzionale:
``` scala
def modifyStrategy: Int => Int
```
### Template Method
Template method è un pattern comportamentale che permette di definire la struttura di un algoritmo lasciando alle sottoclassi il compito di implementarne i passi come preferiscono.  
In questo modo si può ridefinire e personalizzare parte del comportamento nelle varie sottoclassi senza dover specificare ogni volta una struttura comune.  

Nell'applicativo viene fatto uso del pattern Template method per esempio nelle classi che estendono ```AbstractItem```. Il trait ```Item``` possiede un metodo ```use(...)``` che viene subito diviso in due sottoparti: ```applyEffect(...)``` (effettivo uso dello strumento e applicazione dell'effetto) e ```postEffect(...)``` (ciò che accade una volta che lo strumento è stato utilizzato).  
Le sottoclassi di ```AbstractItem``` non dovranno modificare il metodo ```use(...)```, ma sono vincolate a specificare il comportamento delle due sottoparti.
``` scala
def use(): Unit = {
    applyEffect()
    postEffect()
}

// subclasses will specify sub-algorithms
def applyEffect(): Unit 

def postEffect(): Unit
```

### Singleton Pattern
Singleton è un pattern creazionale che punta a far si che, di una data classe, esista una ed una sola istanza globalmente accedibile.  
Nonostante le comodità offerte da questo pattern, può risultare facile abusarne erroneamente; Singleton difatti andrebbe usato solo nei casi in cui si sia certi che non esisterà mai più di un'istanza di una certa classe e mai solo per ottenere un accesso comodo ad un oggetto.  

In ScalaQuest esistono solo due Singleton: l'```ApplicationController``` (in cima alla gerarchia dei controller) e il ```Frame``` (dettato dalla necessità del framework di Swing di mantenere sempre la stessa istanza di Frame per evitare che l'utente veda chiudersi e riaprire la finestra nella GUI).  
``` scala
sealed trait ApplicationController extends Controller {
    ...
}

object ApplicationController extends ApplicationController {
    ...
}
```

### Adapter Pattern
Conosciuto anche con il nome di Wrapper, Adapter è un pattern di design che punta a risolvere un problema di compatibilità tra due oggetti.  
Non è raro che una libreria disponga di una funzionalità utile ma vi sia un problema ad interfacciare i propri dati con quanto richiesto dal framework. Tramite questo pattern viene dunque creato un oggetto che riesca ad adattare i dati di modo da mettere in comunicazione le due interfacce incompatibili.

L'editor fa largo uso della libreria GraphStream, utile per rappresentare a schermo dei grafi contenenti dei nodi; tale framework però è ha un'impostazione diversa rispetto ai nostri StoryNode e Pathway.  
Al fine di poter rappresentare graficamente il nostro model dunque è stata creata la classe ```EdgeInfo``` che sopperisce al problema di incompatibilità dei dati.
``` scala
protected case class EdgeInfo(private val startingNode: StoryNode,
                              private val pathway: Pathway) {

  val isFinalNode: Boolean = pathway.destinationNode.pathways.isEmpty

  val isConditionalEdge: Boolean = pathway.prerequisite.nonEmpty

  ...

}
```

### Facade pattern
Facade è un design pattern usato per esporre una facciata molto semplice che mascheri un sistema complesso al fine di migliorare la leggibilità e usabilità del codice fornendo un unico punto di accesso.  

Questo pattern è risultato fondamentale per gestire lo svolgimento del gioco; i vari SubController che si occupano di diversi aspetti relativi alla partita (StoryController, HistoryController, ProgressSaverController, ecc...) infatti sono messi in comunicazione tra loro tramite il ```GameMasterController``` che sfrutta questo pattern esponendo il metodo ```executeOperation``` così che sia possibile in modo facile cambiare quale Controller abbia la parola in quel momento richiamando.

``` scala
sealed trait GameMasterController extends Controller {

  def executeOperation(operation: OperationType): Unit
}

object GameMasterController {

  private class GameMasterControllerImpl(private val storyModel: StoryModel)
    extends GameMasterController {

    ...

    override def executeOperation(op: OperationType): Unit = op match {
      case OperationType.StoryOperation =>
        subControllersContainer.storyController.execute()
      case OperationType.HistoryOperation =>
        subControllersContainer.historyController.execute()
      ...
    }
  }

  ...
}
```
### Builder Pattern
Il design pattern Builder è un pattern creazionale attraverso il quale semplificare la creazione di un oggetto complesso, rendendo anche possibile creare diverse rappresentazioni di tale oggetto.
L'algoritmo per la creazione di tale oggetto è indipendente dalle varie parti che lo compongono e da come vengono assemblate; ciò ha l'effetto immediato di rendere più semplice la classe, permettendo a una classe builder separata di focalizzarsi sulla corretta costruzione di un'istanza e lasciando che la classe originale si concentri sul funzionamento degli oggetti.  

Come citato precedentemente, i Form dell'editor sono stati costruiti agilmente attraverso il ```FormBuilder```, che permette la creazione di form complessi semplicemente chiamando a catena i suoi metodi per aggiungere campi al form.

``` scala
case class FormBuilder() {

    private val listBuffer: ListBuffer[FormElement] = ListBuffer()

    def addTextAreaField(label: String, oldText: String = ""): FormBuilder =
        addField(TextAreaInputElement(label, oldText))

    def addIntegerField(label: String): FormBuilder =
        addField(IntegerInputElement(label))

    ...

    private def addField(formElement: FormElement): FormBuilder = {
        listBuffer += formElement
        this
    }

  def get(controller: Controller): Form = Form(controller, listBuffer.toList)

}
```

<!-- etc. -->

---