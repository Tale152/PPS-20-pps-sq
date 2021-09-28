# Design di dettaglio

## Scelte rilevanti

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

### Proxy Pattern
TODO descrizione proxy

Questo pattern è risultato fondamentale per gestire lo svolgimento del gioco; i vari SubController che si occupano di diversi aspetti relativi alla partita (StoryController, HistoryController, ProgressSaverController, ecc...) infatti sono messi in comunicazione tra loro tramite il ```GameMasterController``` che sfrutta questo pattern esponendo il metodo ```executeOperation``` così che sia possibile in modo facile cambiare quale Controller abbia la parola in quel momento richiamando.

``` scala
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
### Facade Pattern
Facade è un design pattern usato per esporre una facciata molto semplice che mascheri un sistema complesso al fine di migliorare la leggibilità e usabilità del codice fornendo un unico punto di accesso.

Il Pattern è stato utilizzato all'interno di ``SqPrologEngine``, ovvero la classe che rappresenta il motore del lingugagio Prolog che si occupa di fornire alcuni fatti in output data una certa richiesta in input tramite il metodo ``resolve``.  
Il funzionamente del sistema è particolarmente complesso, ma l'utilizzatore riesce a fruirne in maniera trasparente. L'utente deve solo saper maneggiare le strutture dati di input e output per utilizzare la classe. Se nel nome della classe non venisse menzionato il linguaggio Prolog, probabilmente l'utilizzatore non sarebbe al corrente che viene utilizzato un altro linguagggio per effettuare le computazioni richieste.

``` scala
case class SqPrologEngine(storyNode: StoryNode) {

  ...

  def resolve[A <: Term](goal: A): Stream[A] = ...

  ...
}
```

### Iterator Pattern
Iterator è un pattern comportamentale che viene utilizzato quando si vuole accedere agli elementi di una collezione senza dover esporne la struttura. L’obiettivo è la creazione di un oggetto che esponga sempre gli stessi metodi indipendentemente dall’aggregato di dati.

Il pattern è stato per esempio utilizzato per il reperimento delle soluzioni nella classe ``SqPrologEngine``.  
L'iteratore in questo caso viene utilizzato per aggregare le diverse soluzioni che la libreria tuProlog restituisce.
``` scala
private case class PrologEngineIterator[A <: Term](engine: Prolog, goal: A) extends Iterator[A] {

    override def hasNext: Boolean = {
      // check if there are pending solutions
    }

    override def next(): A = {
      // return a pending solution
    }

```

<!-- etc. -->

---