package bridge

import util.Result
import util.Side

/**
 * 다리 건너기 게임을 관리하는 클래스
 */
class BridgeGame(private val bridges: List<String>) {
    private val upSide = mutableListOf<String>()
    private val downSide = mutableListOf<String>()
    private var bridgeIndex = BEGIN_BRIDGE_INDEX
    private var tryCount = BEGIN_TRY_COUNT

    /**
     * 사용자가 칸을 이동할 때 사용하는 메서드
     *
     *
     * 이동을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    fun move(direction: String) {
        when (direction) {
            bridges[bridgeIndex] -> {
                checkSide(direction, Result.SUCCESS.emoji)
                nextBridge()
            }

            else -> checkSide(direction, Result.FAILURE.emoji)
        }
    }

    /**
     * 사용자가 게임을 다시 시도할 때 사용하는 메서드
     *
     *
     * 재시작을 위해 필요한 메서드의 반환 타입(return type), 인자(parameter)는 자유롭게 추가하거나 변경할 수 있다.
     */
    fun retry() {
        increaseTryCount()
        bridgeIndex = BEGIN_BRIDGE_INDEX
        upSide.clear()
        downSide.clear()
    }

    private fun checkSide(side: String, result: String) {
        when (side) {
            Side.DOWN.command -> addDownSide(result)

            Side.UP.command -> addUpSide(result)
        }
    }

    private fun addUpSide(result: String) {
        upSide.add(result)
        downSide.add(BLANK)
    }

    private fun addDownSide(result: String) {
        upSide.add(BLANK)
        downSide.add(result)
    }

    private fun nextBridge() = bridgeIndex++

    private fun increaseTryCount() = tryCount++

    fun isFail() = downSide.contains(Result.FAILURE.emoji) || upSide.contains(Result.FAILURE.emoji)

    fun isEnd() = bridgeIndex == bridges.size

    fun getUpSide() = upSide.joinToString(" | ", "[ ", " ]")

    fun getDownSide() = downSide.joinToString(" | ", "[ ", " ]\n")

    fun getTryCount() = tryCount

    companion object {
        const val BEGIN_TRY_COUNT = 1
        const val BEGIN_BRIDGE_INDEX = 0
        const val BLANK = " "
    }
}
