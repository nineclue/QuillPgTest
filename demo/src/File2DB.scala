import java.io.{File, InputStreamReader, FileInputStream, BufferedReader}
import scala.collection.JavaConverters._

object File2DB {
    private val csvSplitR = """,(?=([^\"]*\"[^\"]*\")*(?![^\"]*\"))"""
    def apply(f: File, charset: String = "UTF-8"): Iterator[String] = {
        val bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(f), charset))
        bufferedReader.lines().iterator().asScala
    }
    def apply(fname: String): Iterator[String] = apply(new File(fname))
    def splitedString(s: String) = s.split(csvSplitR)
    def make[A](ss: Iterator[String])(implicit maker: Seq[String] => Option[A]): Iterator[A] = 
        ss.map(s => maker(s.split(csvSplitR))).flatten
}