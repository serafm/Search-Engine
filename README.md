# SearchEngineLuceneProject

Αναφορά 1ης Φάσης: Μηχανή Αναζήτησης με την χρήση της βιβλιοθήκης Lucene

Συλλογή Εγγράφων:

Αρχικά συλλέξαμε τα σχετικά με τον κορονοϊό άρθρα μέσω της ιστοσελίδας ScienceDirect.com και της επίσημης ιστοσελίδας του Παγκόσμιου Οργανισμού Υγείας (WHO). 
Συνολικά συλλέξαμε 516 αρχέια όπου θα επεξεργαστούν και θα προβληθούν με την βοήθεια που διατίθεται από την Open-Source βιβλιοθήκη Lucene και Version 3.6.0.

Γενική Περιγραφή Μηχανής Αναζήτησης:

Στόχος για την μηχανή αναζήτησης είναι να εμφανίζει στον χρήστης τα αρχεία που ψάχνει, στο λιγότερο πιθανό χρόνο και με την καλύτερη εμφάνιση αποτελέσματος. Η λειτουργικότητα
της μηχανής αναζήτησης επιτυγχάνεται διαμέσου των διαθέσιμων εργαλειών(κλάσεις,μεθόδους) που μας παρέχει η βιβλιοθήκη Lucene. Με την χρήση των κλάσεων StandardAnalyzer,
IndexWriter, Field, Document δημιουργήσαμε το ευρετήριο και αμέσως μετά με τις κλάσιες-μεθόδους IndexSearcher, IndexReader, QueryParser, TopDocs και ScoreDocs θα επεξεργαστεί
το ευρετήριο και θα εμφανιστούν στον χρήστη τα αποτελέσματα.

Ανάλυση Κειμένου-Κατασκευή Ευρετηρίου:

Τα άρθρα αρχικά είναι σε μορφή PDF και έπειτα από επεξεργασία μετατρέπονται σε μορφή αρχείου TXT. Με την μετατροπή τους σε TXT δεν είναι ακόμη έτοιμα για επεξεργασία.
Εδώ είναι που μπαίνει στο παιχνίδι η βιβλιοθήκη Lucene. Αρχικά με την χρήση της κλάσης Field χωρίζεται το κάθε αρχείο σε 4 πεδία τα οποία είναι το όνομα του αρχείου(FileName),
το μονοπάτι του αρχείου(FilePath), τον τίτλο του αρχείου(TitleName) και το περιεχόμενο του αρχείου(FileContent). Αυτά με την χρήση της κλάσης Document αποθηκεύονται. Αμέσως μετά
με την χρήση των κλάσεων StandardAnalyzer και IndexWriter αναλύονται σε πιό απλή μορφή τα πεδία TitleName και FileContent εφόσον πρώτα ανακτηθούν από την Document. Με αυτό τον
τρόπο υπάρχει ταχύτερη αναζήτηση. Μετά από όλη αυτή την διαδικασία θα δημιουργηθεί το ευρετήριο και θα είναι έτοιμο για οποιοδήποτε τρόπο αναζήτησης επιλέξουμε. Επίσης
δημιουργούμε ένα λεξικό στο οποίο εμπεριέχεται μέσα σχετικές λέξεις κλειδιά σχετικά με τον κορονοϊό.

Αναζήτηση:

Αρχικά θα γίνεται αναζήτηση των άρθρων με τη χρήση Boolean καθώς είναι πιο γρήγορο στην αναζήτηση μέσω του ευρετηρίου. Ο χρήστης θα μπορεί να αναζητά μια φράση, με την χρήση της
QueryParser το πρόγραμμα θα αναλύει και θα διαχωρίζει αυτή την φράση σε λέξεις και θα τις συγκρίνει με το λεξικό. Μετά την σύγκριση θα προβάλλονται στον χρήστη τα άρθρα με τις
περισσότερες προβολές των σχετικών λέξεων από λεξικό με την χρήση των κλασεων TopDocs και ScoreDocs. Οι λέξεις όπως το «και  ,τη ,την κτλ.» δεν θα τις λαμβάνει υπόψη στην
αναζήτηση εκτός και αν αποτελούνται σε συγκεκριμένες φράσεις μέσα στα αρχεία. Επίσης υπάρχει η επιλογή να γίνεται και αναζήτηση στον τίτλο του άρθρου. Όταν ο χρήστης προσπαθεί
να γράψει την φράση του θα παρουσιάζονται προτεινόμενες λέξεις για αυτόν ανάλογα με το τι έχει γράψει μέχρι στιγμής.

Παρουσίαση Αποτελεσμάτων:

Τα αποτελέσματα κάθε αναζήτησης θα παρουσιάζονται στον χρήστη δέκα σε κάθε σελίδα και για το κάθε σχετικό άρθρο θα εμφανίζονται αποσπάσματα του περιεχομένου του με bold
highlight τις λέξεις που έχει αναζητήσει. Επίσης θα υπάρχει και η δυνατότητα προβολής ολόκληρου του αρθρου, θα παρουσιάζεται ο χρόνος αναζητήσης και ο συνολικός αριθμός των
άρθρων που σχετίζονται με την αναζήτηση του χρήστη. Τέλος αν ο χρήστης αναζητήσει κάτι το οποίο είναι άσχετο με το περιεχόμενο των άρθρων θα του παρουσιάζεται ένα μήνυμα
σφάλματος όπου θα τον ενημερώνει για την λάθος αναζήτηση και ότι δεν υπάρχουν αποτελέσματα για αυτό που ψάχνει.
