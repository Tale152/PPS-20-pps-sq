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
ScalaQuest utilizza abbondantemente metodi factory per la creazione di tutti gli oggetti dell'applicazione.
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
ScalaQuest fa uso del pattern Template method per esempio nelle classi che estendono ```AbstractItem```. Il trait ```Item``` possiede un metodo ```use(...)``` che viene subito diviso in due sottoparti: ```applyEffect(...)``` (effettivo uso dello strumento e applicazione dell'effetto) e ```postEffect(...)``` (ciò che accade una volta che lo strumento è stato utilizzato).  
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
<!-- etc. -->

---